package com.hpmath.domain.member.exceptions;

import com.hpmath.common.ErrorCode;

public class ReLoginRequiredException extends AccountException {
    public ReLoginRequiredException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ReLoginRequiredException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
