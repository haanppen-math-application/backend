package com.hpmath.domain.member.exceptions;

import com.hpmath.common.ErrorCode;

public class NotSupportedMemberTypeException extends AccountException {
    public NotSupportedMemberTypeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotSupportedMemberTypeException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
