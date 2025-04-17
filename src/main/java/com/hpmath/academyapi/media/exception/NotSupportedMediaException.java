package com.hpmath.academyapi.media.exception;

import com.hpmath.academyapi.exception.ErrorCode;

public class NotSupportedMediaException extends StorageException {
    public NotSupportedMediaException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotSupportedMediaException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
