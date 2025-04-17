package com.hpmath.hpmathcoreapi.security.exception;

import org.springframework.security.core.AuthenticationException;

public class ExpiredJwtAuthenticationException extends AuthenticationException {
    public ExpiredJwtAuthenticationException(String msg) {
        super(msg);
    }
}
