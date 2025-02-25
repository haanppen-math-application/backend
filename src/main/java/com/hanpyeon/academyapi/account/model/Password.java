package com.hanpyeon.academyapi.account.model;

import lombok.Getter;

@Getter
public class Password {
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
