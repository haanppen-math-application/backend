package com.hpmath.academyapi.dir.exception;

import com.hpmath.academyapi.exception.BusinessException;
import com.hpmath.academyapi.exception.ErrorCode;

public class DirectoryException extends BusinessException {
    public DirectoryException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DirectoryException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
