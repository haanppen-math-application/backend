package com.hpmath.domain.media.exception;

import com.hpmath.common.ErrorCode;

public class NoSuchMediaException extends StorageException {
    public NoSuchMediaException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchMediaException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
