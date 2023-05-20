package com.ctrl.interceptor;


import com.ctrl.entity.CommonResult;
import com.ctrl.utils.JsonUtils;
import com.ctrl.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String requestURI = request.getRequestURI();
        String token = request.getParameter("token");
        if (token == null) {
            sendErrorResponse(response, -9998, "token丢失");
        }
        if (requestURI.contains("/api/login/login")) {
            //判断验证码/token是否失效
            if (validateToken("anti_fan:" + token, 7)) {
                sendErrorResponse(response, -9999, "非法的token");
                return false;
            }
        } else {
            //从缓存中读取用户是否登录
            if (validateToken("anti_fan:" + token, 8)) {
                sendErrorResponse(response, -9997, "未登录，请先登录");
                return false;
            }
            //设置缓存中的时间为两个小时
            redisUtils.expire("anti_fan:" + token, 8, 7200);
        }
        String timeStamp = request.getParameter("timeStamp");
        if (timeStamp == null || !timeStamp.matches("^(\\d{10,13})$")) {
            sendErrorResponse(response, -21000, "请求时时间戳缺失或错误的时间格式");
            return false;
        }
        long localTime = Math.abs(System.currentTimeMillis() / 1000);
        if (localTime - Integer.parseInt(timeStamp) >= 10) {
            sendErrorResponse(response, -20000, "中间人攻击?");
            return false;
        }
        return true;
    }

    /**
     * 验证token
     *
     * @param redisKey     key
     * @param redisDbIndex dbIndex
     * @return boolean
     */
    private boolean validateToken(String redisKey, int redisDbIndex) {
        System.out.println("key=" + ":" + redisKey + "\\" + redisUtils.get(redisKey, redisDbIndex));
        return !Optional.ofNullable(redisUtils.get(redisKey, redisDbIndex)).isPresent();
    }

    /**
     * 发送错误信息
     *
     * @param response     响应
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     */
    private void sendErrorResponse(HttpServletResponse response, int errorCode, String errorMessage) throws IOException {
        CommonResult<Object> error = CommonResult.error(errorCode, errorMessage);
        String beanToJson = JsonUtils.getBeanToJson(error);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(beanToJson != null ? beanToJson : "error");
    }
}
