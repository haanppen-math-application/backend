package com.hpmath.common.jwt;

import com.hpmath.common.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
    public static final String HEADER = "Authorization";
    public static String TOKEN_TYPE = "Bearer";
    private final String MEMBER_NAME = "memberName";
    private final String MEMBER_ROLE = "memberRole";
    @Value("${server.jwt.algorithm}")
    private String encryptAlgorithm;
    @Value("${server.jwt.key}")
    private String jwtKey;
    @Value("${server.jwt.expiration}")
    private Long expirationTime;
    @Value("${server.jwt.refresh.expiration}")
    private Long refreshExpirationTime;
    private SignatureAlgorithm signatureAlgorithm;
    private SecretKey secretKey;
    private JwtParser jwtParser;

    @PostConstruct
    public void initJwt() {
        this.signatureAlgorithm = SignatureAlgorithm.forName(this.encryptAlgorithm);
        final byte[] decodedBytes = Base64.getDecoder().decode(this.jwtKey.getBytes());
        this.secretKey = Keys.hmacShaKeyFor(decodedBytes);
        this.jwtParser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();
    }

    public String generateAccessToken(final Long memberId, final Role role, final String name) {
        Date time = new Date();
        return TOKEN_TYPE + " " + Jwts.builder()
                .setSubject(String.valueOf(memberId))
                .claim(MEMBER_NAME, name)
                .claim(MEMBER_ROLE, role)
                .setIssuedAt(time)
                .setExpiration(new Date(time.getTime() + expirationTime))
                .signWith(secretKey, signatureAlgorithm)
                .compact();
    }

    public String generateRefreshToken(final Long memberId) {
        Date time = new Date();
        return TOKEN_TYPE + " " + Jwts.builder()
                .setSubject(String.valueOf(memberId))
                .setIssuedAt(time)
                .setExpiration(new Date(time.getTime() + refreshExpirationTime))
                .signWith(secretKey, signatureAlgorithm)
                .compact();
    }

    public AuthInfo getAuthInfo(String authorizationHeader) {
        final Claims claims = this.parseToken(authorizationHeader);
        return new AuthInfo(
                getMemberId(claims).orElseThrow(),
                getMemberName(claims).orElse(null),
                getMemberRole(claims).orElse(null)
        );
    }

    private Claims parseToken(String authorizationHeader) {
        String token = authorizationHeader.replace(TOKEN_TYPE, "").trim();
        return jwtParser.parseClaimsJws(token).getBody();
    }

    private Optional<Long> getMemberId(final Claims claims) {
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

    private Optional<String> getMemberName(final Claims claims) {
        return Optional.ofNullable(claims.get(MEMBER_NAME, String.class));
    }

    private Optional<Role> getMemberRole(final Claims claims) {
        final String role = claims.get(MEMBER_ROLE, String.class);
        if (role == null) {
            return Optional.empty();
        }
        return Optional.of(Role.valueOf(role));
    }
}
