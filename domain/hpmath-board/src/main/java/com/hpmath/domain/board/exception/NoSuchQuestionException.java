package com.hpmath.domain.board.exception;

import com.hpmath.common.ErrorCode;

public class NoSuchQuestionException extends BoardException {
    public NoSuchQuestionException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchQuestionException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
