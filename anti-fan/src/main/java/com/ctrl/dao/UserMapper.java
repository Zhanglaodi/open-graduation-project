package com.ctrl.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ctrl.entity.user.UsersDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * The interface Anti fan login mapper.
 *
 * @author dalaodi
 */
@Mapper
public interface UserMapper extends BaseMapper<UsersDO> {
}
