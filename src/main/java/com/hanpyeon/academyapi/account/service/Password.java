package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.validation.PasswordConstraint;
import lombok.Getter;

//@Getter(value = AccessLevel.PACKAGE)
@Getter
public class Password {
    @PasswordConstraint
    private final String password;

    public Password(final String password) {
        this.password = password;
    }

    public boolean isBlank() {
        return password == null || password.isBlank();
    }

    @Override
    public String toString() {
        return "XXXX";
    }
}
