package com.hpmath.domain.board.exception;

import com.hpmath.common.ErrorCode;

public class RequestDeniedException extends BoardException {
    public RequestDeniedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public RequestDeniedException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
