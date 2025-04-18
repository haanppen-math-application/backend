package com.hpmath.hpmathcoreapi.account.exceptions;

import com.hpmath.hpmathcore.ErrorCode;

public class ReLoginRequiredException extends AccountException {
    public ReLoginRequiredException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ReLoginRequiredException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
