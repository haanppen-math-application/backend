package com.hpmath.hpmathwebcommon;

import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PasswordHandlerTest {
    private PasswordEncoder encoder;
    private PasswordHandler passwordHandler;

    PasswordHandlerTest() throws NoSuchAlgorithmException {
        encoder = new PasswordEncoder();
        passwordHandler = new PasswordHandler(encoder);
    }

    @Test
    void test() {
        final String password = "password";

        final String encrypted = passwordHandler.getEncodedPassword(password);
        Assertions.assertAll(
                () -> Assertions.assertEquals(passwordHandler.matches(password, encrypted), true),
                () -> Assertions.assertEquals(passwordHandler.getEncodedPassword(password), encrypted)
        );
    }

}