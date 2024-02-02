package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.security.Role;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class JwtUtilsTest {
    private JwtUtils jwtUtils;

    @BeforeEach
    void initJwtUtils() {
        jwtUtils = new JwtUtils();
    }

    @ParameterizedTest
    @MethodSource("provideArguments")
    void generateToken(Long id, Role role, String name) {
        String token = jwtUtils.generateToken(id, role, name);
        assertThat(token).isNotBlank();
    }

    @ParameterizedTest
    @MethodSource("provideArguments")
    void 토큰_추출_테스트(Long id, Role role, String name) {
        String token = jwtUtils.generateToken(id, role, name);
        Claims claims = jwtUtils.parseToken(token);
        assertThat(jwtUtils.getMemberId(claims)).isEqualTo(Optional.ofNullable(id));
        assertThat(jwtUtils.getMemberRole(claims)).isEqualTo(Optional.ofNullable(role));
        assertThat(jwtUtils.getMemberName(claims)).isEqualTo(Optional.ofNullable(name));
    }

    static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(null, null, null),
                Arguments.of(null, Role.ROLE_MANAGER, "lqkwsmd"),
                Arguments.of((long) 1, null, "lqkwsmd"),
                Arguments.of((long) 1, Role.ROLE_MANAGER, null)
                );
    }
}