package com.qinglan.ai.common.exception;

import lombok.Getter;

/**
 * 业务异常。
 *
 * <p>用于表示注册限制、资源冲突、业务规则校验失败等
 * 可预期的业务错误，由全局异常处理器统一转换为接口响应。
 *
 * @author Kaifan Yang
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * HTTP 状态码
     */
    private final int statusCode;

    public BusinessException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
