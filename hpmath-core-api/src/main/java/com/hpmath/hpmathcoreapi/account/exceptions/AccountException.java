package com.hpmath.hpmathcoreapi.account.exceptions;

import com.hpmath.hpmathcore.BusinessException;
import com.hpmath.hpmathcore.ErrorCode;

public class AccountException extends BusinessException {
    public AccountException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AccountException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
