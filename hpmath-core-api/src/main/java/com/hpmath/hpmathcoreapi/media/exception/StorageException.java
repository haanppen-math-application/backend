package com.hpmath.hpmathcoreapi.media.exception;

import com.hpmath.hpmathcoreapi.exception.BusinessException;
import com.hpmath.hpmathcoreapi.exception.ErrorCode;

public class StorageException extends BusinessException {
    public StorageException(ErrorCode errorCode) {
        super(errorCode);
    }

    public StorageException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
