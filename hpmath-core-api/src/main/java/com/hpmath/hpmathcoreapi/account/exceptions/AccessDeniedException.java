package com.hpmath.hpmathcoreapi.account.exceptions;

import com.hpmath.hpmathcoreapi.exception.ErrorCode;

public class AccessDeniedException extends AccountException{
    public AccessDeniedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AccessDeniedException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
