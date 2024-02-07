package com.hanpyeon.academyapi.security.exception;

import org.springframework.security.core.AuthenticationException;

public class IllegalJwtArgumentException extends AuthenticationException {
    public IllegalJwtArgumentException(String msg) {
        super(msg);
    }
}
