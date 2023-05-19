package com.ctrl.service;

import com.ctrl.entity.CommonResult;
import com.ctrl.entity.raffle.RaffleD0;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

/**
 * The interface Raffle service.
 */
public interface RaffleService {
    /**
     * Raffle common result.
     *
     * @return the common result
     * @throws JsonProcessingException the json processing exception
     */
    CommonResult<List<RaffleD0>> raffle() throws JsonProcessingException;
}
