package com.hpmath.domain.media.exception;

import com.hpmath.common.BusinessException;
import com.hpmath.common.ErrorCode;

public class ChunkException extends BusinessException {

    public ChunkException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ChunkException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
