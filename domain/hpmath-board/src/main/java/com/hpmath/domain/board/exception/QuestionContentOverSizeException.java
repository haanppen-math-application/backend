package com.hpmath.domain.board.exception;

import com.hpmath.common.ErrorCode;

public class QuestionContentOverSizeException extends BoardException {
    public QuestionContentOverSizeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public QuestionContentOverSizeException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
