package com.ctrl.exception;

import com.ctrl.entity.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * The type Global exception.
 *
 * @author dalaodi
 */
@RestControllerAdvice
@Slf4j
public class GlobalException {
    /**
     * 拦截所有Exception类的异常
     *
     * @param e the e
     * @return the map
     */
    @ExceptionHandler(Exception.class)
    public CommonResult<Void> exceptionHandler(Exception e) {
        return CommonResult.error(1000, e.getMessage());
    }

    /**
     * 400 请求参数封装到bean时 类型转换错误
     * 400 (Bad Request)
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class})
    public CommonResult<Void> handleRequestParamFormatError(Exception ex) {
        log.error("handleRequestParamFormatError() bad request ex:{}", ex.getLocalizedMessage());
        return CommonResult.error(400, ex.getLocalizedMessage());
    }

    /**
     * 404 (Not Found)
     *
     * @param ex      the ex
     * @param request the request
     * @return the map
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public CommonResult<Void> handleNoHandlerFoundException(NoHandlerFoundException ex, WebRequest request) {
        log.error("handleNoHandlerFoundException() {}{}{} ", request, " exception message:", ex.getLocalizedMessage());
        return CommonResult.error(404, ex.getLocalizedMessage());
    }

    /**
     * 405 (Method Not Allowed)
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResult<Void> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.error("handleHttpRequestMethodNotSupportedException() request Method Not Allowed(405) exception message:{}", ex.getLocalizedMessage());
        return CommonResult.error(405, ex.getLocalizedMessage());
    }

}
