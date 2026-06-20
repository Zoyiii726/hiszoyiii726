package com.his.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 1. 允许前端跨域携带凭证（如 Cookie、Token）
        config.setAllowCredentials(true);

        // 2. 允许所有源（支持带有通配符的各种前端地址）
        config.addAllowedOriginPattern("*");

        // 3. 允许所有的请求头（避免自定义 Token 被拦截）
        config.addAllowedHeader("*");

        // 4. 允许所有的请求方法（GET, POST, PUT, DELETE, OPTIONS）
        config.addAllowedMethod("*");

        // 对后端所有的接口路径（/api/**）生效
        source.registerCorsConfiguration("/**", config);

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        // 核心关键：将该过滤器的优先级设置为【最高】，确保它在 Spring Security 之前执行！
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return bean;
    }
}