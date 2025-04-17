package com.hpmath.hpmathcoreapi.account.exceptions;

import com.hpmath.hpmathcoreapi.exception.ErrorCode;

public class AlreadyRegisteredException extends AccountException {
    public AlreadyRegisteredException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AlreadyRegisteredException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
