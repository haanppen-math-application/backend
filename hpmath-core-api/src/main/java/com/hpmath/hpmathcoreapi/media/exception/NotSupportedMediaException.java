package com.hpmath.hpmathcoreapi.media.exception;

import com.hpmath.hpmathcoreapi.exception.ErrorCode;

public class NotSupportedMediaException extends StorageException {
    public NotSupportedMediaException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotSupportedMediaException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
