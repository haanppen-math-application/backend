package com.hpmath.academyapi.dir.exception;

import com.hpmath.academyapi.exception.BusinessException;
import com.hpmath.academyapi.exception.ErrorCode;

public class ChunkException extends BusinessException {

    public ChunkException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ChunkException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
