package com.hpmath.hpmathcoreapi.board.exception;

import com.hpmath.hpmathcore.ErrorCode;

public class InvalidTargetException extends BoardException {
    public InvalidTargetException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidTargetException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
