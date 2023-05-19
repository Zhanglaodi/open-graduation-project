package com.ctrl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The type Draw cards.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrawCards implements Serializable {
    private Integer count;
    private Integer total;
}
