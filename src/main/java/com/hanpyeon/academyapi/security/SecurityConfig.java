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
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
                .csrf(csrf -> csrf.disable())
                .cors(httpSecurityCorsConfigurer -> corsConfigurationSource())
                .sessionManagement(session -> session.disable())
                .exceptionHandling(config -> {
                    config.authenticationEntryPoint(new JwtEntryPointHandler());
                    config.accessDeniedHandler(new AccessDeniedHandler());
                })
                .addFilterBefore(new JwtAuthenticationFilter(authenticationManager), AuthorizationFilter.class)
                .authorizeHttpRequests(request -> {
/////////////////////////////////////// 개발 중 열어둠 //////////////////////////////////////
                    request.requestMatchers(request1 -> request1.isUserInRole(Role.ADMIN.getSecurityRole()))
                            .permitAll();

                    request.requestMatchers("/swagger-ui/**")
                            .permitAll();
                    request.requestMatchers("/swagger-ui")
                            .permitAll();
                    request.requestMatchers("/v3/api-docs/**")
                            .permitAll();

                    request.requestMatchers("/api/login")
                            .permitAll();
                    request.requestMatchers("/api/login/refresh")
                            .permitAll();
//////////////////////////////////////////////////////////////////////////////////////////
                    // accounts
                    request.requestMatchers(HttpMethod.POST, "/api/accounts")
                            .hasAnyAuthority(
                                    Role.MANAGER.getSecurityRole(),
                                    Role.TEACHER.getSecurityRole()
                            );
                    request.requestMatchers(HttpMethod.DELETE, "/api/accounts")
                            .hasAnyAuthority(
                                    Role.MANAGER.getSecurityRole(),
                                    Role.ADMIN.getSecurityRole()
                            );
                    request.requestMatchers(HttpMethod.PUT, "/api/accounts/teacher")
                            .hasAnyAuthority(
                                    Role.MANAGER.getSecurityRole(),
                                    Role.ADMIN.getSecurityRole()
                            );
                    request.requestMatchers(HttpMethod.PUT, "/api/accounts/student")
                            .hasAnyAuthority(
                                    Role.MANAGER.getSecurityRole(),
                                    Role.ADMIN.getSecurityRole(),
                                    Role.TEACHER.getSecurityRole()
                            );

                    request.requestMatchers("/api/images/**")
                            .permitAll();

                    request.requestMatchers(HttpMethod.POST, "/api/board/comments")
                            .hasAnyAuthority(
                                    Role.MANAGER.getSecurityRole(),
                                    Role.TEACHER.getSecurityRole());
                    request.requestMatchers(HttpMethod.DELETE, "/api/board/comments/*")
                            .hasAnyAuthority(
                                    Role.MANAGER.getSecurityRole(),
                                    Role.TEACHER.getSecurityRole());
                    request.requestMatchers(HttpMethod.PATCH, "/api/board/comments/*")
                            .hasAnyAuthority(
                                    Role.MANAGER.getSecurityRole(),
                                    Role.TEACHER.getSecurityRole());

                    request.requestMatchers(HttpMethod.POST, "/api/board/questions")
                            .hasAuthority(
                                    Role.STUDENT.getSecurityRole());
//                    request.requestMatchers(HttpMethod.GET, "/api/board/questions/*")
//                            .authenticated();
//                    request.requestMatchers(HttpMethod.GET, "/api/board/questions")
//                            .authenticated();
                    request.requestMatchers(HttpMethod.PATCH, "/api/board/questions/*")
                            .hasAnyAuthority(
                                    Role.STUDENT.getSecurityRole());
                    request.requestMatchers(HttpMethod.DELETE, "/api/board/questions/*")
                            .hasAnyAuthority(
                                    Role.MANAGER.getSecurityRole(),
                                    Role.TEACHER.getSecurityRole());
                    // 반 삭제 API는 매니저만 사용가능 하도록 구현
                    request.requestMatchers(HttpMethod.DELETE, "/api/manage/courses/*")
                            .hasAnyAuthority(Role.MANAGER.getSecurityRole());

                    // DELETE Method 경우 아래 Matcher 는 사용되지 않음
                    request.requestMatchers("/api/manage/courses/**")
                            .hasAnyAuthority(
                                    Role.MANAGER.getSecurityRole(),
                                    Role.TEACHER.getSecurityRole());

                    request.requestMatchers(HttpMethod.GET, "/api/courses")
                            .authenticated();

//                    request.requestMatchers(HttpMethod.GET, "/api/members/teachers")
//                            .hasAnyAuthority(
//                                    Role.MANAGER.getSecurityRole(),
//                                    Role.TEACHER.getSecurityRole()
//                            );

                    // 404 NOT FOUND EXCEPTION
                    request.anyRequest().permitAll();
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

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
