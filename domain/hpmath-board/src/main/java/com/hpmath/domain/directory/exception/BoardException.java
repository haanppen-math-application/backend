package com.hpmath.domain.directory.exception;

import com.hpmath.hpmathcore.BusinessException;
import com.hpmath.hpmathcore.ErrorCode;

public class BoardException extends BusinessException {
    public BoardException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BoardException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
