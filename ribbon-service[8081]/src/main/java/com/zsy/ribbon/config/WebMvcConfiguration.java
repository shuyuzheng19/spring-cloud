package com.zsy.ribbon.config;

import com.zsy.ribbon.interceptor.GlobalInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 郑书宇
 * @create 2022/10/28 20:38
 * @desc
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GlobalInterceptor()).addPathPatterns("/**").excludePathPatterns("/user/login","/user/registered");
    }
}
