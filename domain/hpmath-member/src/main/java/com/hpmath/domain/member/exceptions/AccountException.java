package com.hpmath.domain.member.exceptions;

import com.hpmath.common.BusinessException;
import com.hpmath.common.ErrorCode;

public class AccountException extends BusinessException {
    public AccountException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AccountException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
