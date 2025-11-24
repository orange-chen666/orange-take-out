package com.orange.config;

import com.orange.interceptor.JwtTokenInterceptor;
import com.orange.interceptor.JwtTokenUserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * 配置类，注册web层相关组件
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private JwtTokenInterceptor jwtTokenInterceptor;
    @Autowired
    private JwtTokenUserInterceptor jwtTokenUserInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenInterceptor).
                addPathPatterns("/admin/**").
                excludePathPatterns("/admin/emp/login**");
        registry.addInterceptor(jwtTokenUserInterceptor).
                addPathPatterns("/user/**").
                excludePathPatterns("/user/user/login**");
    }
}
