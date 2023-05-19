package com.ctrl.service.impl;

import com.ctrl.entity.CommonResult;
import com.ctrl.entity.raffle.RaffleD0;
import com.ctrl.service.RaffleConfigService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * The type Raffle config service.
 */
@Service
public class RaffleConfigServiceImpl implements RaffleConfigService {
    /**
     * Sets raffle probability.
     *
     * @return the raffle probability
     */
    @Override
    public CommonResult<String> setRaffleProbability() {
        return null;
    }

}
