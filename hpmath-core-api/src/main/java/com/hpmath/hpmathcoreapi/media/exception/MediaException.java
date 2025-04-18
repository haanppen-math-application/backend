package com.hpmath.hpmathcoreapi.media.exception;

import com.hpmath.hpmathcore.BusinessException;
import com.hpmath.hpmathcore.ErrorCode;

public class MediaException extends BusinessException {
    public MediaException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MediaException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
