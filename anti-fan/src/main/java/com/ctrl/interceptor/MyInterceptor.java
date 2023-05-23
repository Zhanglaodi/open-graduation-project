package com.ctrl.interceptor;

import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 我拦截器
 *
 * @author dalaodi
 * @date 2023 /03/28
 */
@Component
public class MyInterceptor implements HandlerInterceptor {

    private final NamedThreadLocal<Long> threadLocal = new NamedThreadLocal<>("PerformanceMonitor");

    /**
     * 前处理
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理程序
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long startTime = System.currentTimeMillis();
        threadLocal.set(startTime);
        return true;
    }

    /**
     * @param request  请求
     * @param response 响应
     * @param handler  处理程序
     * @param ex       异常
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long endTime = System.currentTimeMillis();
        long startTime = threadLocal.get();
        long elapse = endTime - startTime;
        if (elapse > 0) {
            System.out.printf("%s elapse %d 毫秒%n", request.getRequestURI(), elapse);
        }

    }
}
