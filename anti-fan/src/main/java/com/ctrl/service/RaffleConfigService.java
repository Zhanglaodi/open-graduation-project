package com.ctrl.service;

import com.ctrl.entity.CommonResult;

/**
 * The interface Raffle config service.
 */
public interface RaffleConfigService {
    /**
     * Sets raffle probability.
     *
     * @return the raffle probability
     */
    CommonResult<String> setRaffleProbability();
}
