package com.ctrl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ctrl.dao.AntiFanLoginMapper;
import com.ctrl.entity.CommonResult;
import com.ctrl.entity.ResultException;
import com.ctrl.entity.user.UsersDO;
import com.ctrl.entity.user.UsersLoginDTO;
import com.ctrl.entity.user.UsersVO;
import com.ctrl.service.AntiFanLoginService;
import com.ctrl.utils.CaptchaUtil;
import com.ctrl.utils.JsonUtils;
import com.ctrl.utils.RedisUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.script.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * The type Anti fan login service.
 *
 * @author dalaodi
 */
@Service
public class AntiFanLoginServiceImpl implements AntiFanLoginService {
    /**
     * The Redis utils.
     */
    @Autowired
    RedisUtils redisUtils;

    /**
     * The Request.
     */
    @Resource
    HttpServletRequest request;
    /**
     * The Anti fan login mapper.
     */
    @Resource
    AntiFanLoginMapper antiFanLoginMapper;

    /**
     * Gets captcha.
     *
     * @return the captcha
     */
    @Override
    public CommonResult<Map<String, String>> getCaptcha() {
        String token = UUID.randomUUID().toString();
        CaptchaUtil.Captcha generate = CaptchaUtil.generate(4);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("verification_code", generate.getCode());
        redisUtils.set("anti_fan:" + token, map, 7, 120);
        map.replace("verification_code", generate.getImageCode());
        return CommonResult.ok("获取验证码成功", map);
    }

    /**
     * Login common result.
     *
     * @return the common result
     */
    @Override
    public CommonResult<UsersVO> login(UsersLoginDTO usersLoginDTO) throws JsonProcessingException {
        QueryWrapper<UsersDO> wrapper = Wrappers.query();
        wrapper.eq("phone", usersLoginDTO.getAccount());
        UsersDO xtUser = antiFanLoginMapper.selectOne(wrapper);
        String token = request.getParameter("token");
        UsersDO data =
                Optional.ofNullable(xtUser).orElseThrow(
                        () -> new ResultException(-1, "密码错误或账户不存在1"));
        String s = redisUtils.get("anti_fan:" + token, 7);
        Map jsonToBean = JsonUtils.getJsonToBean(s, Map.class);
        if (!usersLoginDTO.getVerificationCode().equals(jsonToBean.get("verification_code"))) {
            return CommonResult.error(-1, "验证码错误");
        }
        String passwordSha1 = DigestUtils.sha1DigestAsHex(usersLoginDTO.getPassword());
        String password = DigestUtils.sha1DigestAsHex(data.getSalt() + passwordSha1 + data.getSalt());
        if (!password.equals(data.getPassword())) {
            return CommonResult.error(-1, "密码错误或账户不存在2");
        }
        redisUtils.set("anti_fan:" + token, data, 8, 7200);
        redisUtils.delete("anti_fan:" + token, 7);
        return CommonResult.ok("登录成功", new UsersVO(
                data.getUserName(),
                data.getEmail(),
                data.getPhone()));
    }
}
