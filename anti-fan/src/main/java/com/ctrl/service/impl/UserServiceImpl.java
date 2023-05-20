package com.ctrl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ctrl.dao.UserMapper;
import com.ctrl.entity.CommonResult;
import com.ctrl.entity.user.UsersDO;
import com.ctrl.entity.user.UsersVO;
import com.ctrl.service.UserService;
import com.ctrl.utils.JsonUtils;
import com.ctrl.utils.RedisUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * The type User service.
 */
@Service
public class UserServiceImpl implements UserService {
    /**
     * The User mapper.
     */
    @Resource
    UserMapper userMapper;

    /**
     * The Request.
     */
    @Resource
    HttpServletRequest request;

    /**
     * The Redis utils.
     */
    @Resource
    RedisUtils redisUtils;

    /**
     * Gets user info.
     *
     * @return the user info
     */
    @Override
    public CommonResult<UsersVO> getUserInfoByToken() throws JsonProcessingException {
        //从token中取出数据 因为拦截器已经对token验证
        String token = request.getParameter("token");
        String s = redisUtils.get("anti_fan:" + token, 8);
        UsersDO jsonToBean = JsonUtils.fromJsonString(s, UsersDO.class);
        UsersVO usersVO = new UsersVO();
        usersVO.setUserName(jsonToBean.getUserName());
        //当然需要脱敏处理一下 手机号码以及邮箱
        String desensitizationPhone = jsonToBean.getPhone().replaceAll("(^\\d{3})\\d.*(\\d{4})", "$1****$2");
        String desensitizationEmail = jsonToBean.getEmail().replaceAll("(^\\w)[^@]*(@.*$)", "$1****$2");
        usersVO.setEmail(desensitizationEmail);
        usersVO.setPhone(desensitizationPhone);
        usersVO.setAge(jsonToBean.getAge());
        usersVO.setSex(jsonToBean.getSex());

        return CommonResult.ok("获取用户信息成功", usersVO);
    }

    /**
     * Add user common result.
     *
     * @param usersDO the users do
     * @return the common result
     */
    @Override
    public CommonResult<String> addUser(UsersDO usersDO) {
        boolean check = checkPhone(usersDO.getPhone());
        if (check) {
            return CommonResult.error(-1, "手机已被注册,如有疑惑唱跳rap打篮球");
        }
        int insert = userMapper.insert(usersDO);
        if (insert > 0) {
            return CommonResult.ok("添加成功", null);
        }
        return CommonResult.error(-1, "删除失败");
    }

    /**
     * Delete user common result.
     *
     * @param id the id
     * @return the common result
     */
    @Override
    public CommonResult<String> deleteUser(Integer id) {
        QueryWrapper<UsersDO> query = Wrappers.query();
        query.eq("id", id);
        int delete = userMapper.delete(query);
        if (delete > 0) {
            return CommonResult.ok("删除成功", null);
        }
        return CommonResult.error(-1, "删除失败");
    }

    /**
     * check phone检查手机是否存在
     *
     * @param checkPhone check phone
     * @return the boolean
     */
    private boolean checkPhone(String checkPhone) {
        QueryWrapper<UsersDO> query = Wrappers.query();
        query.eq("phone", checkPhone);
        return userMapper.selectOne(query) != null;
    }
}
