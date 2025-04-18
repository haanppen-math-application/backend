package com.hpmath.hpmathwebcommon;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordHandler {
    @Value("${service.password.default}")
    private String DEFAULT_PASSWORD;
    private final PasswordEncoder encoder;


    public String getEncodedPassword(final String password) {
        if (password == null || password.isBlank()) {
            return encoder.encode(DEFAULT_PASSWORD);
        }
        return encoder.encode(password);
    }

    public boolean matches(final String rawPassword, final String encodedPassword) {
        return encodedPassword.equals(encoder.encode(rawPassword));
    }
}
