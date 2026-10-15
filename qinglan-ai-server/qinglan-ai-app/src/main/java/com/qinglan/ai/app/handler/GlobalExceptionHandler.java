package com.qinglan.ai.app.handler;

import com.qinglan.ai.common.exception.BusinessException;
import com.qinglan.ai.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

/**
 * 全局接口异常处理器。
 *
 * <p>统一处理业务异常、请求校验异常和系统异常，
 * 将 Spring MVC 异常转换为青岚标准响应格式。
 *
 * @author Kaifan Yang
 */
@Slf4j
@NullMarked
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 处理业务异常。
     *
     * @param ex 业务异常
     * @return 统一错误响应
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(
            BusinessException ex) {

        return ResponseEntity.status(ex.getStatusCode())
                .body(ApiResponse.failure(
                        ex.getStatusCode(),
                        ex.getMessage()));
    }

    /**
     * 处理请求参数校验异常。
     *
     * @param ex      参数校验异常
     * @param headers 响应头
     * @param status  HTTP状态码
     * @param request 当前请求
     * @return 统一错误响应
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("请求参数不合法");

        return Objects.requireNonNull(handleExceptionInternal(
                ex,
                ApiResponse.failure(400, message),
                headers,
                status,
                request));
    }

    /**
     * 统一转换 Spring MVC 内置异常响应。
     *
     * @param body    原始响应体
     * @param headers 响应头
     * @param status  HTTP状态码
     * @param request 当前请求
     * @return 统一响应
     */
    @Override
    protected ResponseEntity<Object> createResponseEntity(
            @Nullable Object body,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        if (body instanceof ApiResponse<?>) {
            return super.createResponseEntity(
                    body, headers, status, request);
        }

        String message = switch (status.value()) {
            case 400 -> "请求参数错误";
            case 404 -> "请求资源不存在";
            case 405 -> "请求方法不支持";
            case 415 -> "不支持的请求内容类型";
            default -> status.is5xxServerError()
                    ? "系统内部错误"
                    : "请求处理失败";
        };

        return super.createResponseEntity(
                ApiResponse.failure(status.value(), message),
                headers,
                status,
                request);
    }

    /**
     * 处理未预期的系统异常。
     *
     * @param ex 系统异常
     * @return 统一错误响应
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnexpectedException(
            Exception ex) {

        log.error("系统发生未预期异常", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failure(500, "系统内部错误"));
    }
}
