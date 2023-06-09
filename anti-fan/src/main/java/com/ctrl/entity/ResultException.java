package com.ctrl.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 结果异常
 *
 * @author dalaodi
 * @date 2023 /03/28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResultException extends RuntimeException  {
    /**
     * 代码
     */
    private Integer code;
    /**
     * 味精
     */
    private String msg;

    /**
     * 结果异常
     *
     * @param code 代码
     * @param msg  味精
     */
    public ResultException(Integer code, String msg) {
        this.msg = msg;
        this.code = code;
    }
}
