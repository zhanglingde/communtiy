package com.ling.other.config;

import com.ling.other.modules.token.interceptor.IdempotentInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author zhangling
 * @since 2020/9/25 14:42
 */
//@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    IdempotentInterceptor idempotentInterceptor;

    /**
     * 将自定义拦截器注入
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(idempotentInterceptor).addPathPatterns("/**");
    }
}
