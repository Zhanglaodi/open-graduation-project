package com.ctrl.entity.raffle;

import lombok.Data;

import java.io.Serializable;

/**
 * The type Raffle vo.
 */
@Data
public class RaffleVO implements Serializable {
    private String characterName;
    private String level;
    private String headImg;
    private String userId;
    private String cardInfo;
    private String details;
}
