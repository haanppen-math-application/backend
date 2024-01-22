package com.hanpyeon.academyapi.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class PasswordHandler {
    private static final String DEFAULT_PASSWORD = "0000";
    private final PasswordEncoder passwordEncoder;
    public PasswordHandler(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    public String getEncodedPassword(String password){
        if (password == null || password.isBlank()) {
            return passwordEncoder.encode(DEFAULT_PASSWORD);
        }
        return passwordEncoder.encode(password);
    }
}
