package com.hpmath.academyapi.account.exceptions;

import com.hpmath.academyapi.exception.ErrorCode;

public class InvalidPasswordException extends AccountException {
    public InvalidPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
