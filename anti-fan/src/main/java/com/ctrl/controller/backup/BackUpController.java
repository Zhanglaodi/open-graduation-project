package com.ctrl.controller.backup;

import com.ctrl.entity.CommonResult;
import com.ctrl.service.BackUpService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * The type Back up controller.
 */
@RestController
@RequestMapping(value = "/api/back_up")
public class BackUpController {
    @Resource
    BackUpService backUpService;

    /**
     * Back up my sql common result.
     *
     * @return common result
     */
    @PostMapping(value = "back_up_mysql")
    public CommonResult<String> backUpMySql() throws IOException {
        return backUpService.backUpMySql("root", "123456", "wiki");
    }
}
