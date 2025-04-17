package com.hpmath.academyapi.board.exception;

import com.hpmath.academyapi.exception.ErrorCode;

public class RequestDeniedException extends BoardException{
    public RequestDeniedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public RequestDeniedException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
