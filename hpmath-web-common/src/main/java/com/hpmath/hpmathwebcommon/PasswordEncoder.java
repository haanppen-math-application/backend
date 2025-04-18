package com.hpmath.hpmathwebcommon;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.stereotype.Component;

@Component
class PasswordEncoder {
    private final MessageDigest digest;

    public PasswordEncoder() throws NoSuchAlgorithmException {
        digest = MessageDigest.getInstance("SHA-256");
    }

    public String encode(String input) {
        final byte[] bytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));  // 16진수로 포맷
        }
        return hexString.toString();  // 해시된 문자열 반환
    }
}
