package com.ctrl.controller.login;

import com.ctrl.entity.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Login controller.
 *
 * @author dalaodi
 */
@RequestMapping(value = "/api/login")
@RestController
public class LoginController {
    /**
     * Login common result.登录方法
     *
     * @return the common result
     */
    @RequestMapping(value = "/login")
    public CommonResult<String> login() {
        return CommonResult.ok("登录成功", null);
    }
}
