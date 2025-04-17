package com.hpmath.hpmathcoreapi.dir.exception;

import com.hpmath.hpmathcoreapi.exception.BusinessException;
import com.hpmath.hpmathcoreapi.exception.ErrorCode;

public class DirectoryException extends BusinessException {
    public DirectoryException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DirectoryException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
