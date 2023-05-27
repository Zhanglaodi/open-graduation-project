package com.ctrl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Common result.
 *
 * @param <T> the type parameter
 * @author dalaodi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    private Integer code;
    private String message;
    private CountAndData< ? extends T> data;

    /**
     * Ok common result. 成功返回
     *
     * @param <T>     the type parameter
     * @param message the message
     * @param data    the data
     * @return the common result
     */
    public static <T> CommonResult<T> ok(String message, CountAndData<T> data) {
        return new CommonResult<>(200, message, data);
    }

    /**
     * Error common result.
     *
     * @param <T>     the type parameter
     * @param code    the code
     * @param message the message
     * @return the common result
     */
    public static <T> CommonResult<T> error(Integer code, String message) {
        return new CommonResult<>(code, message, null);
    }
}
