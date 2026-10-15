package com.qinglan.ai.auth.service;

import com.qinglan.ai.auth.dto.RegisterRequest;
import com.qinglan.ai.auth.vo.RegisterVO;

/**
 * 用户认证业务接口。
 *
 * <p>定义用户注册、登录及身份认证相关业务能力。
 *
 * @author Kaifan Yang
 */
public interface AuthService {

    /**
     * 注册用户。
     *
     * @param request 注册请求
     * @return 注册结果
     */
    RegisterVO register(RegisterRequest request);
}
