package com.hanpyeon.academyapi.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtils {
    public static final String HEADER = "Authorization";
    private final String TOKEN_TYPE = "Bearer";
    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SIGNATURE_ALGORITHM);
    private final String MEMBER_NAME = "memberName";
    private final String MEMBER_ROLE = "memberRole";
    private final long EXPIRATION_TIME = 1000 * 60 * 30; // 1초 -> 1분 -> 30분
    private final JwtParser jwtParser = Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build();

    public String generateToken(final Long memberId, final Role role, final String name) {
        Date time = new Date();
        return TOKEN_TYPE + " " + Jwts.builder()
                .setSubject(String.valueOf(memberId))
                .claim(MEMBER_NAME, name)
                .claim(MEMBER_ROLE, role)
                .setIssuedAt(time)
                .setExpiration(new Date(time.getTime() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SIGNATURE_ALGORITHM)
                .compact();
    }

    // 필터 에러 전역처리
    public Claims parseToken(String authorizationHeader) {
        if (!authorizationHeader.contains(TOKEN_TYPE)) {
            throw new IllegalArgumentException();
        }
        String token = authorizationHeader.replace(TOKEN_TYPE, "").trim();
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public Optional<Long> getMemberId(final Claims claims) {
        try {
            String subject = claims.getSubject();
            Long memberId = Long.parseLong(subject);
            return Optional.ofNullable(memberId);
        } catch (NullPointerException exception) {
            return Optional.empty();
        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
    }

    public Optional<String> getMemberName(final Claims claims) {
        try {
            return Optional.ofNullable(claims.get(MEMBER_NAME, String.class));
        } catch (RequiredTypeException exception) {
            return Optional.empty();
        } catch (NullPointerException exception) {
            return Optional.empty();
        }
    }

    public Optional<Role> getMemberRole(final Claims claims) {
        try {
            return Optional.ofNullable(Role.valueOf(claims.get(MEMBER_ROLE, String.class)));
        } catch (NullPointerException exception) {
            return Optional.empty();
        }
    }
}
