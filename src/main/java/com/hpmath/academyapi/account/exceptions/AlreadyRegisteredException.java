package com.hpmath.academyapi.account.exceptions;

import com.hpmath.academyapi.exception.ErrorCode;

public class AlreadyRegisteredException extends AccountException {
    public AlreadyRegisteredException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AlreadyRegisteredException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
