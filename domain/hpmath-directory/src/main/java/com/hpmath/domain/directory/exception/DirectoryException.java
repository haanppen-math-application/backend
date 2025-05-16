package com.hpmath.domain.directory.exception;

import com.hpmath.hpmathcore.BusinessException;
import com.hpmath.hpmathcore.ErrorCode;

public class DirectoryException extends BusinessException {
    public DirectoryException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DirectoryException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
