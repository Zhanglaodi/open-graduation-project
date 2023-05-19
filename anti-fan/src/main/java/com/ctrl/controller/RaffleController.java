package com.ctrl.controller;

import com.ctrl.entity.CommonResult;
import com.ctrl.entity.raffle.RaffleD0;
import com.ctrl.service.RaffleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * The type Raffle controller.
 */
@RestController
@RequestMapping(value = "/api/raffle")
@Slf4j
public class RaffleController {
    /**
     * The Raffle service.
     */
    @Resource
    RaffleService raffleService;

    /**
     * Draw cards common result.
     *
     * @return the common result
     * @throws JsonProcessingException the json processing exception
     */
    @PostMapping(value = "/draw_cards")
    public CommonResult<List<RaffleD0>> drawCards() throws JsonProcessingException {
        return raffleService.raffle();
    }
}
