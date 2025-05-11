package com.hpmath.domain.member.exceptions;

import com.hpmath.hpmathcore.ErrorCode;

public class InvalidPasswordException extends AccountException {
    public InvalidPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
