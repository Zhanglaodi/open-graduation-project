package com.org.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.org.example.entity.RaffleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RaffleRecordMapper extends BaseMapper<RaffleVO> {

    int insertList(@Param("list") List<RaffleVO> raffleVO);
}
