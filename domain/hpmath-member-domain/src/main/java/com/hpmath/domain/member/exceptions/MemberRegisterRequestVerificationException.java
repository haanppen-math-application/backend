package com.hpmath.domain.member.exceptions;

import com.hpmath.hpmathcore.ErrorCode;

public class MemberRegisterRequestVerificationException extends AccountException {
    public MemberRegisterRequestVerificationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemberRegisterRequestVerificationException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
