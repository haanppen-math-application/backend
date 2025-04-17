package com.hpmath.academyapi.media.exception;

import com.hpmath.academyapi.exception.ErrorCode;

public class MediaStoreException extends StorageException {
    public MediaStoreException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MediaStoreException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
