package com.ctrl.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ctrl.entity.raffle.RaffleDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * The interface Raffle mapper.
 */
@Mapper
public interface RaffleMapper extends BaseMapper<RaffleDO> {
}
