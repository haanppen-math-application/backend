package com.hanpyeon.academyapi.security;

import com.hanpyeon.academyapi.security.exceptionhandler.AccessDeniedHandler;
import com.hanpyeon.academyapi.security.exceptionhandler.JwtEntryPointHandler;
import com.hanpyeon.academyapi.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import java.util.List;


@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
        return httpSecurity
                .csrf(csrf ->
                        csrf.disable()
                )
                .exceptionHandling(config -> {
                    config.authenticationEntryPoint(new JwtEntryPointHandler());
                    config.accessDeniedHandler(new AccessDeniedHandler());
                })
                .addFilterBefore(new JwtAuthenticationFilter(authenticationManager), AuthorizationFilter.class)
                .authorizeHttpRequests(http ->
                        http.anyRequest().permitAll()
                ).build();
    }

    @Bean
    AuthenticationManager authenticationManager(List<AuthenticationProvider> providers) {
        return new ProviderManager(providers);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B);
    }
}
