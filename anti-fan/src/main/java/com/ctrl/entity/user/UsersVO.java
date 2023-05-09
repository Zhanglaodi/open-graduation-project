package com.ctrl.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * The type User vo.
 *
 * @author dalaodi
 */
@Data
@AllArgsConstructor
public class UsersVO implements Serializable {
    private String userName;
    private String email;
    private String phone;
}
