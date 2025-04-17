package com.hpmath.hpmathcoreapi.course.application.exception;

import com.hpmath.hpmathcoreapi.exception.BusinessException;
import com.hpmath.hpmathcoreapi.exception.ErrorCode;

public class MemoMediaException extends BusinessException {
    public MemoMediaException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemoMediaException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
