package com.hpmath.domain.member.exceptions;

import com.hpmath.hpmathcore.ErrorCode;

public class AccessDeniedException extends AccountException {
    public AccessDeniedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AccessDeniedException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
