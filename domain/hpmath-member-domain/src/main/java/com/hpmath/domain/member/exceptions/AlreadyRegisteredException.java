package com.hpmath.domain.member.exceptions;

import com.hpmath.hpmathcore.ErrorCode;

public class AlreadyRegisteredException extends AccountException {
    public AlreadyRegisteredException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AlreadyRegisteredException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
