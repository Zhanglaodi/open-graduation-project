package com.ctrl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ctrl.dao.UserMapper;
import com.ctrl.entity.CommonResult;
import com.ctrl.entity.CountAndData;
import com.ctrl.entity.user.UsersDO;
import com.ctrl.entity.user.UsersLoginDTO;
import com.ctrl.entity.user.UsersVO;
import com.ctrl.service.AntiFanLoginService;
import com.ctrl.utils.RedisUtils;
import com.example.utils.CaptchaUtil;
import com.example.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
    UserMapper antiFanLoginMapper;

    /**
     * Gets captcha.
     *
     * @return the captcha
     */
    @Override
    public CommonResult<Map<String, String>> getCaptcha() {
        //uuid生成一个token
        String token = UUID.randomUUID().toString();
        //随机生成四位的验证码
        CaptchaUtil.Captcha generate = CaptchaUtil.generate(4);
        //把验证码存放到hashmap中 这里存在的问题就是hashmap容易出现空指针问题 最好实体映射
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("verification_code", generate.getCode());
        //下面的代码的意思是7号库缓存时间为120秒
        redisUtils.set("anti_fan:" + token, map, 7, 120);
        map.replace("verification_code", generate.getImageCode());
        return CommonResult.ok("获取验证码成功", new CountAndData<>(null, map));
    }

    /**
     * Login common result.
     *
     * @return the common result
     */
    @Override
    public CommonResult<UsersVO> login(UsersLoginDTO usersLoginDTO) throws JsonProcessingException {
        //mybatis plus的条件 根据手机号查询用户
        QueryWrapper<UsersDO> wrapper = Wrappers.query();
        wrapper.eq("phone", usersLoginDTO.getAccount());
        //请求中必须带有token
        String token = request.getParameter("token");
        Optional<UsersDO> xtUser =
                Optional.ofNullable(antiFanLoginMapper.selectOne(wrapper));
        //假如为空就返回用户不存在的提示
        if (!xtUser.isPresent()) {
            return CommonResult.error(-1, "密码错误或账户不存在");
        }
        UsersDO data = xtUser.get();
        //获取token 如果七号库的token信息不存在那就提示验证密码过期
        Optional<String> sToken = Optional.ofNullable(redisUtils.get("anti_fan:" + token, 7));
        if (!sToken.isPresent()) {
            return CommonResult.error(-1, "验证码过期");
        }
        String s = sToken.get();
        //反序列化7号库数据
        Map jsonToBean = JsonUtils.fromJsonString(s, Map.class);
        //通过比较请求中的验证码和内存中的验证码对比
        String code = jsonToBean.get("verification_code").toString();
        if (!usersLoginDTO.getVerificationCode().equalsIgnoreCase(code)) {
            return CommonResult.error(-1, "验证码错误");
        }
        //密码先用sha1加密一次
        String passwordSha1 = DigestUtils.sha1DigestAsHex(usersLoginDTO.getPassword());
        //加密后的password拼接密码盐再一次加密
        String password = DigestUtils.sha1DigestAsHex(data.getSalt() + passwordSha1);
        log.info(password);
        //比对前端加密后的密码与数据库是否一致
        if (!password.equals(data.getPassword())) {
            return CommonResult.error(-1, "密码错误或账户不存在");
        }
        //登录成功删除七号库数据 把用户部分信息存储到八号库
        redisUtils.set("anti_fan:" + token, data, 8, 7200);
        redisUtils.delete("anti_fan:" + token, 7);
        return CommonResult.ok("登录成功", null);
    }

    /**
     * exit 退出
     *
     * @return CommonResult<String>
     */
    @Override
    public CommonResult<String> exit() {
        String requestToken = request.getParameter("token");
        redisUtils.delete("anti_fan:" + requestToken, 8);
        return CommonResult.ok("退出成功", null);
    }
}
