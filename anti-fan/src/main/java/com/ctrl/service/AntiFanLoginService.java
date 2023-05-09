package com.ctrl.service;

import com.ctrl.entity.CommonResult;
import com.ctrl.entity.user.UsersLoginDTO;
import com.ctrl.entity.user.UsersVO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

/**
 * The interface Anti fan login service.
 *
 * @author dalaodi
 */
public interface AntiFanLoginService {
    /**
     * Gets captcha.
     *
     * @return the captcha
     */
    CommonResult<Map<String, String>> getCaptcha();


    /**
     * Login common result.
     *
     * @param usersLoginDTO the users login dto
     * @return the common result
     * @throws JsonProcessingException the json processing exception
     */
    CommonResult<UsersVO> login(UsersLoginDTO usersLoginDTO) throws JsonProcessingException;
}
