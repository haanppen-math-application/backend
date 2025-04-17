package com.hpmath.hpmathcoreapi.media.exception;

import com.hpmath.hpmathcoreapi.exception.ErrorCode;

public class NoSuchMediaException extends StorageException {
    public NoSuchMediaException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchMediaException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
