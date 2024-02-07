package com.hanpyeon.academyapi.security.authentication;

import com.hanpyeon.academyapi.security.JwtUtils;
import com.hanpyeon.academyapi.security.Role;
import com.hanpyeon.academyapi.security.exception.IllegalJwtArgumentException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtUtils jwtUtils;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token;
        try {
            token = (String) authentication.getCredentials();
        } catch (ClassCastException exception) {
            throw new IllegalJwtArgumentException("적합한 토큰 형식이 아닙니다.");
        }

        try {
            Claims claims = jwtUtils.parseToken(token);

            MemberPrincipal memberPrincipal = new MemberPrincipal(getMemberId(claims), getMemberName(claims));
            return JwtAuthenticationToken.authenticated(token, memberPrincipal, getMemberRole(claims));

        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException exception) {
            throw new IllegalJwtArgumentException("사용할 수 없는 JWT 입니다.");
        } catch (ExpiredJwtException exception) {
            throw new IllegalJwtArgumentException("만료된 토큰 입니다. 토큰을 재발급 받아주세요");
        }

    }

    private Long getMemberId(Claims claims) {
        return jwtUtils.getMemberId(claims).stream()
                .findAny()
                .orElseThrow(() -> {
                    throw new IllegalJwtArgumentException("Cannot Find MemberId");
                });
    }

    private String getMemberName(Claims claims) {
        return jwtUtils.getMemberName(claims).stream()
                .findAny()
                .orElseThrow(() -> {
                    throw new IllegalJwtArgumentException("Cannot Find MemberName");
                });
    }

    private Collection<? extends GrantedAuthority> getMemberRole(Claims claims) {
        Role role = jwtUtils.getMemberRole(claims).stream()
                .findAny()
                .orElseThrow(() -> {
                    throw new IllegalJwtArgumentException("Cannot Find MemberRole");
                });
        return List.of(new SimpleGrantedAuthority(role.getSecurityRole()));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        if (authentication.isAssignableFrom(JwtAuthenticationToken.class)) {
            return true;
        }
        return false;
    }
}
