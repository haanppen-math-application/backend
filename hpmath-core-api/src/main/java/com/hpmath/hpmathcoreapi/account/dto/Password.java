package com.hpmath.hpmathcoreapi.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hpmath.hpmathcoreapi.account.validation.PasswordConstraint;
import com.hpmath.hpmathcoreapi.security.PasswordHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Password {
    @PasswordConstraint
    @JsonProperty
    private final String rawPassword;

    public boolean isMatch(final String encryptedPassword, final PasswordHandler passwordHandler) {
        return passwordHandler.matches(rawPassword, encryptedPassword);
    }

    public String getEncryptedPassword(final PasswordHandler passwordHandler) {
        return passwordHandler.getEncodedPassword(rawPassword);
    }

    @Override
    public String toString() {
        return "XXXX";
    }
}
