package com.hanpyeon.academyapi.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class PasswordHandler {
    private static final String DEFAULT_PASSWORD = "0000";
    private final PasswordEncoder passwordEncoder;

    public PasswordHandler(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String getEncodedPassword(final String password) {
        if (password == null || password.isBlank()) {
            return passwordEncoder.encode(DEFAULT_PASSWORD);
        }
        return passwordEncoder.encode(password);
    }

    public boolean matches(final String rawPassword, final String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
