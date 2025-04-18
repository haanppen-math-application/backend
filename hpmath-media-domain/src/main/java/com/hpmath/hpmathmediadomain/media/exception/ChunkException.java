package com.hpmath.hpmathmediadomain.media.exception;

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
