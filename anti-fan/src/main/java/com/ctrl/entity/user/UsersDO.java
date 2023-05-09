package com.ctrl.entity.user;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * The type Users do.
 *
 * @author dalaodi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDO implements Serializable {
    /**
     * The interface Add method.
     */
    public interface AddMethod {

    }

    /**
     * The interface Edit method.
     */
    public interface EditMethod {

    }

    /**
     * ID
     */
    @Null(message = "新增时id必须为空", groups = {AddMethod.class})
    @NotNull(message = "更新时id不能为空", groups = {EditMethod.class})
    @ApiModelProperty("ID")
    private Integer id;
    /**
     * 用户名
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @ApiModelProperty("用户名")
    @JsonProperty("user_name")
    @NotBlank(message = "用户名称不能为空", groups = {AddMethod.class})
    private String userName;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String updateTime;
    /**
     * 性别
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @ApiModelProperty("性别")
    private String sex;
    /**
     * 年龄
     */
    @ApiModelProperty("年龄")
    private Integer age;
    /**
     * 邮箱
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @ApiModelProperty("邮箱")
    private String email;
    /**
     * 手机
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @ApiModelProperty("手机")
    @NotNull(message = "手机不能为空")
    @Pattern(regexp = "^[1][3,4,5,7,8][0-9]{9}$", message = "电话号码有误", groups = {AddMethod.class})
    private String phone;

    /**
     * salt
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @ApiModelProperty("密码盐")
    private String salt;
    /**
     * password
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @ApiModelProperty("密码")
    private String password;
}
