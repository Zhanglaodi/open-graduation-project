package com.ctrl.config;

import com.ctrl.interceptor.AuthInterceptor;
import com.ctrl.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * The type Common config.
 *
 * @author dalaodi
 */
@Configuration
public class CommonConfig implements WebMvcConfigurer {
    private static final List<String> EXCLUDE_PATH = Arrays.asList("/", "/upload/**", "/css/**", "/js/**", "/img/**", "/media/**", "/vendors/**", "/favicon.ico", "/false", "/404_not_found");
    /**
     * The My interceptor.
     */
    @Resource
    MyInterceptor myInterceptor;


    /**
     * The Auth interceptor.
     */
    @Resource
    AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor).
                addPathPatterns("/**");
        registry.addInterceptor(authInterceptor).
                addPathPatterns("/**")
                .excludePathPatterns("/api/login/get_captcha").excludePathPatterns(EXCLUDE_PATH);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //是否发送Cookie
                .allowCredentials(true)
                //放行哪些原始域
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT")
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
}
