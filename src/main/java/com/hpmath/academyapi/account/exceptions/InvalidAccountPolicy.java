package com.hpmath.academyapi.account.exceptions;

import com.hpmath.academyapi.exception.ErrorCode;

public class InvalidAccountPolicy extends AccountException{
    public InvalidAccountPolicy(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidAccountPolicy(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
