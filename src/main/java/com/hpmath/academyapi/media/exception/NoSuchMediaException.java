package com.hpmath.academyapi.media.exception;

import com.hpmath.academyapi.exception.ErrorCode;

public class NoSuchMediaException extends StorageException {
    public NoSuchMediaException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchMediaException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
