package com.hpmath.hpmathcoreapi.dir.exception;

import com.hpmath.hpmathcore.BusinessException;
import com.hpmath.hpmathcore.ErrorCode;

public class ChunkException extends BusinessException {

    public ChunkException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ChunkException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
