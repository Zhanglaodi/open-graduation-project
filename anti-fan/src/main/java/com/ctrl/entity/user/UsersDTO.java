package com.ctrl.entity.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 * The type Users dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UsersDTO {
    @NotEmpty(message = "用户名称不能为空")
    @ApiModelProperty("用户名称")
    private String userName;
    private String email;
    @NotEmpty(message = "手机号不能为空")
    @ApiModelProperty("手机号")
    private String phone;
    private Integer age;
    private String sex;
    private String avatar;
}
