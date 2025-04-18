package com.hpmath.hpmathcoreapi.board.exception;

import com.hpmath.hpmathcore.ErrorCode;

public class QuestionContentOverSizeException extends BoardException{
    public QuestionContentOverSizeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public QuestionContentOverSizeException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
