package com.hpmath.academyapi.account.exceptions;

import com.hpmath.academyapi.exception.ErrorCode;

public class NotSupportedMemberTypeException extends AccountException {
    public NotSupportedMemberTypeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotSupportedMemberTypeException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
