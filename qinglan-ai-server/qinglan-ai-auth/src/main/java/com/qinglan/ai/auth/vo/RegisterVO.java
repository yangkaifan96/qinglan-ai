package com.qinglan.ai.auth.vo;

/**
 * 用户注册响应对象。
 *
 * <p>返回新注册用户的基础信息，
 * 不包含密码及其他敏感字段。
 *
 * @author Kaifan Yang
 */
public record RegisterVO(
        Long id,
        String username,
        String nickname
) {
}
