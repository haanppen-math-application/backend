package com.hpmath.hpmathcoreapi.board.exception;

import com.hpmath.hpmathcore.ErrorCode;

public class AlreadySolvedQuestionException extends BoardException{
    public AlreadySolvedQuestionException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AlreadySolvedQuestionException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
