package com.hpmath.domain.media.exception;

import com.hpmath.common.BusinessException;
import com.hpmath.common.ErrorCode;

public class StorageException extends BusinessException {
    public StorageException(ErrorCode errorCode) {
        super(errorCode);
    }

    public StorageException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
