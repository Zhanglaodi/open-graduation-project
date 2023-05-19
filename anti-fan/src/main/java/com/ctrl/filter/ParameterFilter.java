package com.ctrl.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * The type Parameter filter.
 *
 * @author 张宪泰
 */
@WebFilter(urlPatterns = "/api/*", filterName = "parameterFilter")
@Order(2)
public class ParameterFilter implements Filter {
    /**
     * 初始化
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    /**
     * @param req         请求
     * @param resp        响应
     * @param filterChain 拦截
     * @throws IOException      io异常
     * @throws ServletException servlet异常
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(req, resp);
    }

    /**
     *
     */
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
