package com.hanpyeon.academyapi.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class PasswordHandlerTest {
    private static final String OTHER = "OTHER";
    private static final String DEFAULT = "DEFAULT";

    @Mock
    PasswordEncoder passwordEncoder;
    private PasswordHandler passwordHandler;
    @BeforeEach
    void initPasswordHandler() {
        this.passwordHandler = new PasswordHandler(passwordEncoder);
    }
    @ParameterizedTest
    @MethodSource("provideIllegalPasswords")
    void 비밀번호_초기값_테스트(String inputPassword) {
        Mockito.when(passwordEncoder.encode(ArgumentMatchers.eq("0000"))).thenReturn(DEFAULT);

        Assertions.assertThat(passwordHandler.getEncodedPassword(inputPassword)).isEqualTo(DEFAULT);
        Assertions.assertThat(passwordHandler.getEncodedPassword(null)).isEqualTo(DEFAULT);
    }
    @ParameterizedTest
    @MethodSource("provideLegalPasswords")
    void 비밀번호_사용자값_테스트(String inputPassword) {
        Mockito.when(passwordEncoder.encode(ArgumentMatchers.argThat(arg -> !arg.equals("0000")))).thenReturn(OTHER);

        Assertions.assertThat(passwordHandler.getEncodedPassword(inputPassword)).isEqualTo(OTHER);
    }

    static Stream<Arguments> provideIllegalPasswords() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of("\t"),
                Arguments.of("  \n")
        );
    }
    static Stream<Arguments> provideLegalPasswords() {
        return Stream.of(
                Arguments.of("qwerwqer"),
                Arguments.of("qwerdas"),
                Arguments.of("wefsd123tfe"),
                Arguments.of("efwye5"),
                Arguments.of("wqereler")
        );
    }
}
