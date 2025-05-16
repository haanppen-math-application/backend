package com.hpmath.domain.board.exception;

import com.hpmath.common.ErrorCode;

public class InvalidTargetException extends BoardException {
    public InvalidTargetException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidTargetException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
