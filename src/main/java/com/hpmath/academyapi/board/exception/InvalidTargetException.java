package com.hpmath.academyapi.board.exception;

import com.hpmath.academyapi.exception.ErrorCode;

public class InvalidTargetException extends BoardException {
    public InvalidTargetException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidTargetException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
