package com.ctrl.filter;

import com.ctrl.entity.CommonResult;
import com.ctrl.exception.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type My filter.
 *
 * @author dalaodi
 */
@WebFilter(urlPatterns = "/api/*", filterName = "exceptionFilter")
@Order(-1)
@Component(value = "myExceptionFilter")
public class ExceptionFilter implements Filter {

    /**
     * The Global exception.
     */
    @Autowired
    GlobalException globalException;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            CommonResult<Void> voidCommonResult = globalException.exceptionHandler(e);
            HttpServletResponse res = (HttpServletResponse) servletResponse;
            res.setStatus(voidCommonResult.getCode());
            res.getWriter().write(voidCommonResult.getMessage());
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
