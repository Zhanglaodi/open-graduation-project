package com.org.example.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class RaffleVO implements Serializable {
    private String characterName;
    private String level;
    private String headImg;
    private String userId;
    private String cardInfo;
    private String details;
}
