package com.hpmath.academyapi.course.application.exception;

import com.hpmath.academyapi.exception.BusinessException;
import com.hpmath.academyapi.exception.ErrorCode;

public class MemoMediaException extends BusinessException {
    public MemoMediaException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemoMediaException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
