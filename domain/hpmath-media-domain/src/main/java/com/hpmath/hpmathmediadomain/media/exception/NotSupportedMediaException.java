package com.hpmath.hpmathmediadomain.media.exception;

import com.hpmath.hpmathcore.ErrorCode;

public class NotSupportedMediaException extends StorageException {
    public NotSupportedMediaException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotSupportedMediaException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
