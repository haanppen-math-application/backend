package com.hanpyeon.academyapi.security;

import com.hanpyeon.academyapi.security.exceptionhandler.AccessDeniedHandler;
import com.hanpyeon.academyapi.security.exceptionhandler.JwtEntryPointHandler;
import com.hanpyeon.academyapi.security.filter.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import java.util.List;


@EnableWebSecurity
@Configuration
@SecurityScheme(
        name = "jwtAuth", // API 문서에서 참조될 보안 스키마의 이름
        type = SecuritySchemeType.HTTP, // 보안 스키마의 타입
        scheme = "bearer", // HTTP 스키마
        bearerFormat = "JWT" // 베어러 포맷
)
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
        return httpSecurity
                .csrf(csrf ->
                        csrf.disable()
                )
                .cors(cors ->
                        cors.disable()
                )
                .exceptionHandling(config -> {
                    config.authenticationEntryPoint(new JwtEntryPointHandler());
                    config.accessDeniedHandler(new AccessDeniedHandler());
                })
                .addFilterBefore(new JwtAuthenticationFilter(authenticationManager), AuthorizationFilter.class)
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("/swagger-ui/**").permitAll();
                    request.requestMatchers("/swagger-ui").permitAll();
                    request.requestMatchers("/v3/api-docs/**").permitAll();

                    request.requestMatchers("/api/login").permitAll();
                    request.requestMatchers("/api/accounts").hasAnyAuthority(
                            Role.MANAGER.getSecurityRole(),
                            Role.ADMIN.getSecurityRole(),
                            Role.TEACHER.getSecurityRole());

                    request.requestMatchers("/api/images/**").permitAll();

                    request.requestMatchers(HttpMethod.POST, "/api/board/comments").hasAnyAuthority(
                            Role.MANAGER.getSecurityRole(),
                            Role.TEACHER.getSecurityRole());
                    request.requestMatchers(HttpMethod.DELETE, "/api/board/comments/*").hasAnyAuthority(
                            Role.MANAGER.getSecurityRole(),
                            Role.TEACHER.getSecurityRole());
                    request.requestMatchers(HttpMethod.PATCH, "/api/board/comments/*").hasAnyAuthority(
                            Role.STUDENT.getSecurityRole());

                    request.requestMatchers(HttpMethod.POST, "/api/board/questions").hasAuthority(
                            Role.STUDENT.getSecurityRole());
                    request.requestMatchers(HttpMethod.GET, "/api/board/questions/*").authenticated();
                    request.requestMatchers(HttpMethod.GET, "/api/board/questions").authenticated();
                    request.requestMatchers(HttpMethod.PATCH, "/api/board/questions/*").hasAnyAuthority(
                            Role.STUDENT.getSecurityRole());
                    request.requestMatchers(HttpMethod.DELETE, "/api/board/questions/*").hasAnyAuthority(
                            Role.MANAGER.getSecurityRole(),
                            Role.TEACHER.getSecurityRole());

                    request.requestMatchers(HttpMethod.POST, "/api/courses").hasAnyAuthority(
                            Role.MANAGER.getSecurityRole(),
                            Role.TEACHER.getSecurityRole());

                    request.anyRequest().hasAuthority(
                            Role.ADMIN.getSecurityRole());
                })
                .build();
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
