package com.hpmath.hpmathcoreapi.board.exception;

import com.hpmath.hpmathcoreapi.exception.ErrorCode;

public class QuestionValidationException extends BoardException{
    public QuestionValidationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public QuestionValidationException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
