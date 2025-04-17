package com.hpmath.academyapi.account.exceptions;

import com.hpmath.academyapi.exception.BusinessException;
import com.hpmath.academyapi.exception.ErrorCode;

public class AccountException extends BusinessException {
    public AccountException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AccountException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
