package com.hpmath.domain.course.exception;

import com.hpmath.common.BusinessException;
import com.hpmath.common.ErrorCode;

public class MemoMediaException extends BusinessException {
    public MemoMediaException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemoMediaException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
