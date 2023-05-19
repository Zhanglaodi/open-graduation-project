package com.ctrl.interceptor;


import com.ctrl.entity.ResultException;
import com.ctrl.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 鉴权
 *
 * @author 张宪泰
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    /**
     * The Redis utils.
     */
    @Autowired
    RedisUtils redisUtils;


    /**
     * 预处理器
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理
     * @return boolean 布尔
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("请求路劲：" + request.getRequestURI());
        String token =
                Optional.ofNullable(request.getParameter("token")).orElseThrow(
                        () -> new ResultException(-9998, "token丢失")
                );
        if (request.getRequestURI().contains("/api/login/login")) {
            //判断验证码/token是否失效
            Optional.ofNullable(redisUtils.get("anti_fan:" + token, 7)).orElseThrow(
                    () -> new ResultException(-9999, "非法的token")
            );
        } else {
            //从缓存中读取用户是否登录
            Optional.ofNullable(redisUtils.get("anti_fan:" + token, 8)).orElseThrow(
                    () -> new ResultException(-9997, "未登录")
            );
            //设置缓存中的时间为两个小时
            redisUtils.expire("anti_fan:" + token, 8, 7200);
        }
        //请求中的时间戳
        Optional.ofNullable(request.getParameter("timeStamp")).map(var -> {
            if (var.matches("^(\\d{10,13})$")) {
                long localTime = Math.abs(System.currentTimeMillis() / 1000);
                System.out.println("localTime = " + localTime);
                if (localTime - Integer.parseInt(var) >= 10) {
                    throw new ResultException(-20000, "中间人攻击?");
                }
                return 0;
            } else {
                System.out.println(request.getParameter("timeStamp"));
                throw new ResultException(-20001, "错误的时间格式");
            }
        }).orElseThrow(
                () -> new ResultException(-21000, "请求时时间戳缺失")
        );
        //权限会热修改吗？
        return true;
    }
}
