package com.hpmath.domain.member.exceptions;

import com.hpmath.common.ErrorCode;

public class InvalidAccountPolicy extends AccountException {
    public InvalidAccountPolicy(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidAccountPolicy(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
