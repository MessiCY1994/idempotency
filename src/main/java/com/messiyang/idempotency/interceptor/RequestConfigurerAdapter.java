package com.messiyang.idempotency.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RequestConfigurerAdapter implements WebMvcConfigurer {

    //如果不这么写，在拦截器中就不能注入任何类
    @Bean
    public ApiIdempotentInterceptor setApiIdempotentInterceptor(){
        return new ApiIdempotentInterceptor();
    }
    @Bean
    public AccessLimitInterceptor setAccessLimitInterceptor(){
        return new AccessLimitInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // TODO Auto-generated method stub
        registry.addInterceptor(setApiIdempotentInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(setAccessLimitInterceptor()).addPathPatterns("/**");
    }
}