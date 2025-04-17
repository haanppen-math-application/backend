package com.hpmath.hpmathcoreapi.account.exceptions;

import com.hpmath.hpmathcoreapi.exception.ErrorCode;

public class NotSupportedMemberTypeException extends AccountException {
    public NotSupportedMemberTypeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotSupportedMemberTypeException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
