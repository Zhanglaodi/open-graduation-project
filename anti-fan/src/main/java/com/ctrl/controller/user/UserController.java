package com.ctrl.controller.user;

import com.ctrl.entity.CommonResult;
import com.ctrl.entity.user.UsersDO;
import com.ctrl.entity.user.UsersVO;
import com.ctrl.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

/**
 * The type User controller.
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    /**
     * The User service.
     */
    @Resource
    UserService userService;

    /**
     * Gets user info.
     *
     * @return the user info
     * @throws JsonProcessingException the json processing exception
     */
    @PostMapping(value = "/getUserInfo")
    public CommonResult<UsersVO> getUserInfo() throws JsonProcessingException {
        return userService.getUserInfoByToken();
    }

    /**
     * Add user common result.
     *
     * @param usersDO the users dto
     * @return the common result
     */
    @PostMapping(value = "/addUser")
    public CommonResult<String> addUser(@Validated({UsersDO.AddMethod.class}) @Valid UsersDO usersDO) {
        return userService.addUser(usersDO);
    }


    /**
     * Delete user common result.
     *
     * @param id the id
     * @return the common result
     */
    @PostMapping(value = "/deleteUser")
    public CommonResult<String> deleteUser(@Pattern(regexp = "^[1-9]\\d$") @Valid Integer id) {
        return userService.deleteUser(id);
    }
}
