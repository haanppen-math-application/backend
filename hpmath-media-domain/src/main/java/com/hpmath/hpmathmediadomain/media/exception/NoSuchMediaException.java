package com.hpmath.hpmathmediadomain.media.exception;

import com.hpmath.hpmathcore.ErrorCode;

public class NoSuchMediaException extends StorageException {
    public NoSuchMediaException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchMediaException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
