package com.hpmath.hpmathcoreapi.account.exceptions;

import com.hpmath.hpmathcoreapi.exception.ErrorCode;

public class InvalidAccountPolicy extends AccountException{
    public InvalidAccountPolicy(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidAccountPolicy(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
