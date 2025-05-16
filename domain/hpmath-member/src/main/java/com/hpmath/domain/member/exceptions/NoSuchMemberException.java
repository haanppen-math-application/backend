package com.hpmath.domain.member.exceptions;

import com.hpmath.common.ErrorCode;

public class NoSuchMemberException extends AccountException {
    public NoSuchMemberException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchMemberException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
