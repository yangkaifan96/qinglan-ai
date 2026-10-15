package com.qinglan.ai.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 用户注册请求对象。
 *
 * <p>接收并校验用户首次注册时提交的
 * 登录账号、密码及昵称。
 *
 * @param username 登录账号
 * @param password 登录密码
 * @param nickname 用户昵称
 * @author Kaifan Yang
 */
public record RegisterRequest(

        @NotBlank(message = "登录账号不能为空")
        @Size(max = 64, message = "登录账号不能超过64位")
        @Pattern(
                regexp = "^[a-zA-Z][a-zA-Z0-9_]*$",
                message = "账号必须以字母开头，只能包含字母、数字和下划线"
        )
        String username,

        @NotBlank(message = "登录密码不能为空")
        @Size(min = 8, message = "密码不能少于8位")
        String password,

        @NotBlank(message = "用户昵称不能为空")
        @Size(max = 64, message = "昵称不能超过64位")
        String nickname

) {
}
