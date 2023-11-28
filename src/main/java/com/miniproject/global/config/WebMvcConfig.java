package com.miniproject.global.config;

import com.miniproject.global.resolver.LoginInfoArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final LoginInfoArgumentResolver loginInfoArgumentResolver;

    public WebMvcConfig(LoginInfoArgumentResolver loginInfoArgumentResolver) {
        this.loginInfoArgumentResolver = loginInfoArgumentResolver;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOriginPatterns("http://localhost:8080/**","http://localhost:5173/**")
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowCredentials(true)
            .exposedHeaders("*");

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginInfoArgumentResolver);
    }
}
