package com.ctrl.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The type User vo.
 *
 * @author dalaodi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersVO implements Serializable {
    private String userName;
    private String email;
    private String phone;
    private Integer age;
    private String sex;
    private String avatar;
}
