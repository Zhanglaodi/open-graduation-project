package com.ctrl.entity.raffle;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * The type Raffle d 0.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
@TableName(value = "anti_fan_raffle")
public class RaffleDO implements Serializable {

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
     * id
     */
    @Null(message = "新增时id必须为空", groups = {RaffleDO.AddMethod.class})
    @NotNull(message = "更新时id不能为空", groups = {RaffleDO.EditMethod.class})
    @ApiModelProperty("ID")
    private Integer id;

    /**
     * 角色名称
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @ApiModelProperty("用户名")
    @JsonProperty("user_name")
    @NotBlank(message = "用户名称不能为空", groups = {RaffleDO.AddMethod.class})
    private String characterName;

    /**
     * 卡牌等级
     */
    @ApiModelProperty("卡牌等级")
    @NotBlank(message = "用户名称不能为空", groups = {RaffleDO.AddMethod.class})
    private Integer level;

    /**
     * 海报
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @ApiModelProperty("图片地址")
    private String postImg;

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
     * 卡牌基础防御
     */
    @ApiModelProperty("卡牌基础防御")
    private Integer baseDefense;

    /**
     * 基础力量
     */
    @ApiModelProperty("卡牌基础防御")
    private Integer baseStrength;

    /**
     * 基础速度
     */
    @ApiModelProperty("卡牌基础速度")
    private Integer baseSpeed;

    /**
     * 基础血量
     */
    @ApiModelProperty("卡牌基础血量")
    private Integer baseHealth;

    /**
     * 技能
     */
    @ApiModelProperty("卡牌技能")
    private String skills;
}
