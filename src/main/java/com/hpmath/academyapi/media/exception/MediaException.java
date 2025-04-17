package com.hpmath.academyapi.media.exception;

import com.hpmath.academyapi.exception.BusinessException;
import com.hpmath.academyapi.exception.ErrorCode;

public class MediaException extends BusinessException {
    public MediaException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MediaException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
