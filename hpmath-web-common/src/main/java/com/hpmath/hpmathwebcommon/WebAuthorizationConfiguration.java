package com.hpmath.hpmathwebcommon;

import com.hpmath.hpmathwebcommon.authenticationV2.AuthorizationInterceptor;
import com.hpmath.hpmathwebcommon.authenticationV2.JwtAuthenticationFilter;
import com.hpmath.hpmathwebcommon.authenticationV2.LoginIdMethodArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAuthorizationConfiguration {
    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils();
    }

    @Bean
    public OncePerRequestFilter jwtAuthenticationFilter(final JwtUtils jwtUtils) {
        return new JwtAuthenticationFilter(jwtUtils);
    }

    @Bean
    public WebMvcConfigurer authenticationWebMvcConfigurer() {
        return new WebAuthorizationConfig();
    }

    private static class WebAuthorizationConfig implements WebMvcConfigurer {
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new AuthorizationInterceptor())
                    .addPathPatterns("/api/**");
        }

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
            resolvers.add(new LoginIdMethodArgumentResolver());
        }
    }
}
