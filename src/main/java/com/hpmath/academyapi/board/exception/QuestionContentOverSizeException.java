package com.hpmath.academyapi.board.exception;

import com.hpmath.academyapi.exception.ErrorCode;

public class QuestionContentOverSizeException extends BoardException{
    public QuestionContentOverSizeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public QuestionContentOverSizeException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
