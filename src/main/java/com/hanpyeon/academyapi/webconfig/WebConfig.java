package com.hanpyeon.academyapi.webconfig;

import com.hanpyeon.academyapi.aspect.log.LogInterceptor;
import jakarta.servlet.http.Cookie;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("abc", HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN)
                .allowCredentials(true);
    }
}
