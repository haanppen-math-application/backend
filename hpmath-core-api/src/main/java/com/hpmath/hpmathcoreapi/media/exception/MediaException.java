package com.hpmath.hpmathcoreapi.media.exception;

import com.hpmath.hpmathcoreapi.exception.BusinessException;
import com.hpmath.hpmathcoreapi.exception.ErrorCode;

public class MediaException extends BusinessException {
    public MediaException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MediaException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
