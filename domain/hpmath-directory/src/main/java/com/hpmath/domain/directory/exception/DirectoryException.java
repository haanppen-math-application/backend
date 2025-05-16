package com.hpmath.domain.directory.exception;

import com.hpmath.common.BusinessException;
import com.hpmath.common.ErrorCode;

public class DirectoryException extends BusinessException {
    public DirectoryException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DirectoryException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
