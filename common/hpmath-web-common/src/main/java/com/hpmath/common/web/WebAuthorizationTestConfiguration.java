package com.hpmath.common.web;

import com.hpmath.common.Role;
import com.hpmath.common.web.authentication.MemberPrincipal;
import com.hpmath.common.web.authenticationV2.AuthorizationInterceptor;
import com.hpmath.common.web.authenticationV2.JwtRequestWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 테스트 환경에서 컨트롤러의 인증 및 인가 로직을 검증하기 위해 사용하는 구성 클래스입니다.
 <p>
 이 구성은 실서비스에서 사용되는 보안 구성(JwtAuthenticationFilter, AuthorizationInterceptor, MethodArgumentResolver)을 대체하지 않고,
 테스트 환경에서만 작동하는 인증 시뮬레이션용 보안 구성입니다.
 <p>
 사용자는 MockMvc를 통해 요청을 수행할 때, {@code request.setAttribute("role", ...)} 형태로 역할 정보를 주입할 수 있으며,
 내부적으로 {@link TestAuthorizationFilter}가 이를 읽어 {@code Principal}을 구성합니다.
 <p>
 구성된 Principal은 {@link AuthorizationInterceptor} 등 인가 로직에 의해 실제 사용자처럼 해석되므로,
 역할(Role)에 따라 컨트롤러의 접근 허용 여부를 테스트할 수 있습니다.
 <pre>{@code
mockMvc.perform(get("/api/admin")
.requestAttr("role", Role.ADMIN))
.andExpect(status().isOk());
}</pre>
 @see TestAuthorizationFilter
 @see AuthorizationInterceptor
 @see WebAuthorizationConfig */

class WebAuthorizationTestConfiguration {

    @Bean
    OncePerRequestFilter testAuthorizationFilter() {
        return new TestAuthorizationFilter();
    }

    @Bean
    WebMvcConfigurer webMvcConfigurer() {
        return new WebAuthorizationConfig();
    }

    private static class WebAuthorizationConfig implements WebMvcConfigurer {
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new AuthorizationInterceptor())
                    .addPathPatterns("/api/**");
        }
    }

    private static class TestAuthorizationFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                        FilterChain filterChain) throws ServletException, IOException {
            final Role role = (Role) request.getAttribute("role");
            logger.debug("Testing role: " + role);
            if (role != null) {
                filterChain.doFilter(new JwtRequestWrapper(request, new MemberPrincipal(-1L, role)), response);
            }
        }
    }
}
