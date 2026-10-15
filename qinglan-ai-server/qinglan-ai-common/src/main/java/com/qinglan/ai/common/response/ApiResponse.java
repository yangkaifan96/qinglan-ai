package com.qinglan.ai.common.response;

/**
 * 统一接口响应对象。
 *
 * <p>规范 REST API 的响应结构，
 * 包含业务状态码、提示信息和响应数据。
 *
 * @param code    业务状态码，0表示成功
 * @param message 响应提示信息
 * @param data    响应数据
 * @param <T>     响应数据类型
 * @author Kaifan Yang
 */
public record ApiResponse<T>(
        int code,
        String message,
        T data
) {

    /**
     * 返回成功结果。
     *
     * @param data 响应数据
     * @return 成功响应
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(0, "success", data);
    }

    /**
     * 返回不包含数据的成功结果。
     *
     * @return 成功响应
     */
    public static ApiResponse<Void> success() {
        return new ApiResponse<>(0, "success", null);
    }

    /**
     * 返回失败结果。
     *
     * @param code    错误码
     * @param message 错误信息
     * @return 失败响应
     */
    public static <T> ApiResponse<T> failure(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }
}
