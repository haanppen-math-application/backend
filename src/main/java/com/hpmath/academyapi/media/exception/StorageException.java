package com.hpmath.academyapi.media.exception;

import com.hpmath.academyapi.exception.BusinessException;
import com.hpmath.academyapi.exception.ErrorCode;

public class StorageException extends BusinessException {
    public StorageException(ErrorCode errorCode) {
        super(errorCode);
    }

    public StorageException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
