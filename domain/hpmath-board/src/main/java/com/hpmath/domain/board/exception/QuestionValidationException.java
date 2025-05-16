package com.hpmath.domain.board.exception;

import com.hpmath.common.ErrorCode;

public class QuestionValidationException extends BoardException {
    public QuestionValidationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public QuestionValidationException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
