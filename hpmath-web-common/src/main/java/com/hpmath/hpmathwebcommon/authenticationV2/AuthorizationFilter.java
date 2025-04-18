package com.hpmath.hpmathwebcommon.authenticationV2;

import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathwebcommon.JwtUtils;
import com.hpmath.hpmathwebcommon.authentication.MemberPrincipal;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
@RequiredArgsConstructor
class AuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(JwtUtils.HEADER);

        if (containsJwtAuthorization(authorizationHeader)) {
            final Claims claims = jwtUtils.parseToken(authorizationHeader);

            final Role role = jwtUtils.getMemberRole(claims).orElseThrow();
            final Long userId = jwtUtils.getMemberId(claims).orElseThrow();

            filterChain.doFilter(new JwtRequestWrapper(request, new MemberPrincipal(userId, role)), response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean containsJwtAuthorization(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith(JwtUtils.TOKEN_TYPE);
    }
}
