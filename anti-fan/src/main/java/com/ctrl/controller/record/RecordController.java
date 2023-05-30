package com.ctrl.controller.record;

import com.ctrl.entity.CommonResult;
import com.ctrl.entity.CountAndData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Record controller.
 */
@RestController
@RequestMapping(value = "/api/record")
public class RecordController {

    /**
     * Query list common result.
     *
     * @return the common result
     */
    @PostMapping(value = "/queryList")
    public CommonResult<List<String>> queryList(
    ) {
        CountAndData<List<String>> a = null;
        return CommonResult.ok("查询成功", a);
    }
}
