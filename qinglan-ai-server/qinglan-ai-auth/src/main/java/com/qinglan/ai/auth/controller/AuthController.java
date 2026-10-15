package com.qinglan.ai.auth.controller;

import com.qinglan.ai.auth.dto.RegisterRequest;
import com.qinglan.ai.auth.service.AuthService;
import com.qinglan.ai.auth.vo.RegisterVO;
import com.qinglan.ai.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 用户认证接口。
 *
 * <p>提供用户注册及后续登录、退出等认证入口。
 * 具体认证业务由 AuthService 负责。
 *
 * @author Kaifan Yang
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 用户注册。
     *
     * @param request 注册请求
     * @return 注册结果
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RegisterVO> register(
            @Valid @RequestBody RegisterRequest request) {
        return ApiResponse.success(authService.register(request));
    }
}
