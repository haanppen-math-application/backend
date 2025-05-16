package com.hpmath.domain.media.exception;

import com.hpmath.common.BusinessException;
import com.hpmath.common.ErrorCode;

public class MediaException extends BusinessException {
    public MediaException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MediaException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
