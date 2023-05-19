package com.ctrl.controller;

import com.ctrl.entity.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Error controller.
 *
 * @author 张宪泰
 */
@RestController
public class ErrorController {
    /**
     * Not login object.
     *
     * @param request the request
     * @return the object
     */
    @RequestMapping(value = "/false")
    public CommonResult<Void> notLogin(HttpServletRequest request) {
        Exception errorMessage = (Exception) request.getAttribute("errorMessage");
        return CommonResult.error(-1, errorMessage.getMessage());
    }
}
