package com.hpmath.hpmathcoreapi.course.application.exception;

import com.hpmath.hpmathcore.BusinessException;
import com.hpmath.hpmathcore.ErrorCode;

public class MemoMediaException extends BusinessException {
    public MemoMediaException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemoMediaException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
