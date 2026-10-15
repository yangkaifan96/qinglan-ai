package com.qinglan.ai.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.qinglan.ai.auth.dto.RegisterRequest;
import com.qinglan.ai.auth.entity.SysUser;
import com.qinglan.ai.auth.mapper.SysUserMapper;
import com.qinglan.ai.auth.service.AuthService;
import com.qinglan.ai.auth.vo.RegisterVO;
import com.qinglan.ai.common.enums.DeploymentMode;
import com.qinglan.ai.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

/**
 * 用户认证业务实现类。
 *
 * <p>负责用户注册、账号唯一性校验、
 * 注册策略检查及密码安全存储。
 *
 * @author Kaifan Yang
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SysUserMapper sysUserMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * BCrypt 密码最大有效字节数
     */
    private static final int BCRYPT_MAX_PASSWORD_BYTES = 72;

    /**
     * 青岚部署模式
     */
    @Value("${qinglan.deployment.mode:LOCAL}")
    private DeploymentMode deploymentMode;

    /**
     * 是否开放用户注册
     */
    @Value("${qinglan.auth.registration-enabled:false}")
    private boolean registrationEnabled;


    /**
     * 注册用户。
     *
     * @param request 注册请求
     * @return 注册结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RegisterVO register(RegisterRequest request) {

        // 校验注册策略
        long userCount = sysUserMapper.selectCount(null);
        checkRegistrationAllowed(userCount);

        // 校验账号唯一性
        long exists = sysUserMapper.selectCount(
                Wrappers.<SysUser>lambdaQuery()
                        .eq(SysUser::getUsername, request.username())
        );

        if (exists > 0) {
            throw new BusinessException(409, "登录账号已存在");
        }

        // BCrypt 密码长度限制
        if (request.password().getBytes(StandardCharsets.UTF_8).length > BCRYPT_MAX_PASSWORD_BYTES) {
            throw new BusinessException(400, "密码不能超过72字节");
        }

        // 创建用户
        SysUser user = new SysUser();
        user.setUsername(request.username());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setNickname(request.nickname());
        user.setStatus(1);

        try {
            sysUserMapper.insert(user);
        } catch (DuplicateKeyException ex) {
            throw new BusinessException(409, "登录账号已存在");
        }

        return new RegisterVO(
                user.getId(),
                user.getUsername(),
                user.getNickname()
        );
    }

    /**
     * 校验当前实例是否允许注册用户。
     *
     * @param userCount 当前已注册用户数量
     */
    private void checkRegistrationAllowed(long userCount) {
        if (userCount == 0) {
            return;
        }

        if (deploymentMode == DeploymentMode.LOCAL) {
            throw new BusinessException(403, "当前实例已关闭注册");
        }

        if (!registrationEnabled) {
            throw new BusinessException(403, "当前实例已关闭注册");
        }
    }
}
