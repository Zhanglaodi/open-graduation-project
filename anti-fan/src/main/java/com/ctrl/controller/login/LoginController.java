package com.ctrl.controller.login;

import com.ctrl.entity.CommonResult;
import com.ctrl.entity.user.UsersLoginDTO;
import com.ctrl.entity.user.UsersVO;
import com.ctrl.service.AntiFanLoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

/**
 * The type Login controller.
 *
 * @author dalaodi
 */
@RequestMapping(value = "/api/login")
@RestController
public class LoginController {
    /**
     * The Anti fan login service.
     */
    @Resource
    AntiFanLoginService antiFanLoginService;

    /**
     * Login common result.登录方法
     *
     * @param usersLoginDTO the users login dto
     * @return the common result
     * @throws JsonProcessingException the json processing exception
     */
    @RequestMapping(value = "/login")
    public CommonResult<UsersVO> login(@Validated @Valid UsersLoginDTO usersLoginDTO) throws JsonProcessingException {
        return antiFanLoginService.login(usersLoginDTO);
    }


    /**
     * Gets captcha.获取验证码 当然不能让验证码被无限制的获取 令牌
     *
     * @return the captcha
     */
    @GetMapping(value = "/get_captcha")
    public CommonResult<Map<String, String>> getCaptcha() {
        return antiFanLoginService.getCaptcha();
    }


    /**
     * Exit common result.
     *
     * @return the common result
     */
    @RequestMapping(value = "/exit")
    public CommonResult<String> exit() {
        return antiFanLoginService.exit();
    }
}
