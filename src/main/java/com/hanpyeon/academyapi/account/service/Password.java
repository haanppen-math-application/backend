package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.validation.PasswordConstraint;
import com.hanpyeon.academyapi.security.PasswordHandler;
import lombok.Getter;

//@Getter(value = AccessLevel.PACKAGE)
@Getter
public class Password {
    @PasswordConstraint
    private final String rawPassword;

    public Password(final String password) {
        this.rawPassword = password;
    }

    public boolean isBlank() {
        return rawPassword == null || rawPassword.isBlank();
    }

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
