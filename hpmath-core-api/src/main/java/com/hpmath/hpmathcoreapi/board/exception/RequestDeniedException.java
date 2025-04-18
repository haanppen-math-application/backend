package com.hpmath.hpmathcoreapi.board.exception;

import com.hpmath.hpmathcore.ErrorCode;

public class RequestDeniedException extends BoardException{
    public RequestDeniedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public RequestDeniedException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
