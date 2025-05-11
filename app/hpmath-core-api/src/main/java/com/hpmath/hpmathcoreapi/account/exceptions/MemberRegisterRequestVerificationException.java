package com.hpmath.hpmathcoreapi.account.exceptions;

import com.hpmath.hpmathcore.ErrorCode;

public class MemberRegisterRequestVerificationException extends AccountException {
    public MemberRegisterRequestVerificationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemberRegisterRequestVerificationException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
