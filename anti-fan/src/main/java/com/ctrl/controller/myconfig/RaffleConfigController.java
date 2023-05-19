package com.ctrl.controller.myconfig;

import com.ctrl.entity.CommonResult;
import com.ctrl.service.RaffleConfigService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * The type Config controller.
 */
@RestController
@RequestMapping(value = "/api/config")
public class RaffleConfigController {

    /**
     * The Raffle config service.
     */
    @Resource
    RaffleConfigService raffleConfigService;

    /**
     * Sets raffle probability.
     *
     * @return the raffle probability
     */
    @PatchMapping(value = "setRaffle")
    public CommonResult<String> setRaffleProbability() {
        return raffleConfigService.setRaffleProbability();
    }
}
