package com.hpmath.hpmathcoreapi.media.exception;

import com.hpmath.hpmathcore.ErrorCode;

public class MediaStoreException extends StorageException {
    public MediaStoreException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MediaStoreException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
