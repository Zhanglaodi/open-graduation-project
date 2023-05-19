package com.ctrl.entity.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The type Users login dto.
 *
 * @author dalaodi
 */
@Data
public class UsersLoginDTO implements Serializable {
    /**
     * account
     */
    @NotEmpty(message = "账户不能为空")
    @ApiModelProperty("account")
    private String account;
    /**
     * password
     */
    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty("password")
    private String password;

    /**
     * verificationCode
     */
    @NotEmpty(message = "验证码不能为空")
    @ApiModelProperty("verificationCode")
    private String verificationCode;

    /**
     * loginType
     */
    @NotNull(message = "登陆方式不能为空")
    @ApiModelProperty("loginType")
    private Integer loginType;
}
