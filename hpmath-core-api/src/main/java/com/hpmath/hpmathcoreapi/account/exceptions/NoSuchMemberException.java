package com.hpmath.hpmathcoreapi.account.exceptions;

import com.hpmath.hpmathcoreapi.exception.ErrorCode;

public class NoSuchMemberException extends AccountException {
    public NoSuchMemberException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchMemberException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
