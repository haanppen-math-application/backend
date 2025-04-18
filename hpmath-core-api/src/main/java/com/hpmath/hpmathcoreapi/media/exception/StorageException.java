package com.hpmath.hpmathcoreapi.media.exception;

import com.hpmath.hpmathcore.BusinessException;
import com.hpmath.hpmathcore.ErrorCode;

public class StorageException extends BusinessException {
    public StorageException(ErrorCode errorCode) {
        super(errorCode);
    }

    public StorageException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
