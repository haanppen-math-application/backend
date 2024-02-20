package com.hanpyeon.academyapi.account.exceptions;

import com.hanpyeon.academyapi.exception.ErrorCode;

public class MemberRoleVerificationException extends AccountException {
    public MemberRoleVerificationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemberRoleVerificationException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
