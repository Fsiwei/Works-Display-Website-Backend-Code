package com.example.springboot.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author : siwei.fan
 * @date : 2024/3/3 17:50
 * @modyified By :
 */

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
    
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor()) // 配置 jwt 的拦截规则
                .addPathPatterns("/**"); // 拦截所有的请求路径
                // .excludePathPatterns("/api/file/**");
        super.addInterceptors(registry);
    }
    
    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
    
}
