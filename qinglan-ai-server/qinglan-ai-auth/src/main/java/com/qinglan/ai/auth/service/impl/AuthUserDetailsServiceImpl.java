package com.qinglan.ai.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.qinglan.ai.auth.entity.SysUser;
import com.qinglan.ai.auth.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 数据库用户认证服务。
 *
 * <p>根据登录账号查询 sys_user 表，
 * 将用户信息转换为 Spring Security 所需的认证对象，
 * 并校验账号是否处于可用状态。
 *
 * @author Kaifan Yang
 */
@NullMarked
@Service
@RequiredArgsConstructor
public class AuthUserDetailsServiceImpl implements UserDetailsService {

    private final SysUserMapper sysUserMapper;

    /**
     * 根据登录账号加载用户认证信息。
     *
     * @param username 登录账号
     * @return 用户认证信息
     * @throws UsernameNotFoundException 用户不存在
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        SysUser user = sysUserMapper.selectOne(
                Wrappers.<SysUser>lambdaQuery()
                        .eq(SysUser::getUsername, username)
        );

        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        return User.withUsername(user.getUsername())
                .password(user.getPasswordHash())
                .authorities(Collections.emptyList())
                .disabled(user.getStatus() == null || user.getStatus() != 1)
                .build();
    }
}
