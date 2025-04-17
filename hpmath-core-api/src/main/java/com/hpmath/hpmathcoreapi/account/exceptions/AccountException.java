package com.hpmath.hpmathcoreapi.account.exceptions;

import com.hpmath.hpmathcoreapi.exception.BusinessException;
import com.hpmath.hpmathcoreapi.exception.ErrorCode;

public class AccountException extends BusinessException {
    public AccountException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AccountException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
