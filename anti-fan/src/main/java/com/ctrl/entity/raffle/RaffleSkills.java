package com.ctrl.entity.raffle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * The type Raffle skills.
 */
@Data
public class RaffleSkills {
    private String name;
    private String description;
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
}
