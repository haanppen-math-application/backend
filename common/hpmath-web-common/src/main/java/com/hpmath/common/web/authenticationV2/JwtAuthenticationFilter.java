package com.hpmath.common.web.authenticationV2;

import com.hpmath.common.jwt.AuthInfo;
import com.hpmath.common.jwt.JwtUtils;
import com.hpmath.common.Role;
import com.hpmath.common.web.authentication.MemberPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(JwtUtils.HEADER);

        if (containsJwtAuthorization(authorizationHeader)) {
            final Role role;
            final Long userId;
            try {
                final AuthInfo authInfo = jwtUtils.getAuthInfo(authorizationHeader);
                role = authInfo.role();
                userId = authInfo.memberId();
            } catch (Exception e) {
                log.warn("Error parsing authorization header : {}", e.toString());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            filterChain.doFilter(new JwtRequestWrapper(request, new MemberPrincipal(userId, role)), response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean containsJwtAuthorization(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith(JwtUtils.TOKEN_TYPE);
    }
}
