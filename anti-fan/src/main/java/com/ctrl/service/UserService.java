package com.ctrl.service;

import com.ctrl.entity.CommonResult;
import com.ctrl.entity.user.UsersDO;
import com.ctrl.entity.user.UsersVO;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Gets user info.
     *
     * @return the user info
     * @throws JsonProcessingException the json processing exception
     */
    CommonResult<UsersVO> getUserInfoByToken() throws JsonProcessingException;

    /**
     * Add user common result.
     *
     * @param usersDTO the users dto
     * @return the common result
     */
    CommonResult<String> addUser(UsersDO usersDTO);

    /**
     * Delete user common result.
     *
     * @param id the id
     * @return the common result
     */
    CommonResult<String> deleteUser(Integer id);
}
