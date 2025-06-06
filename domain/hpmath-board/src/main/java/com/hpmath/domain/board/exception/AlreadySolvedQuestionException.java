package com.hpmath.domain.board.exception;

import com.hpmath.common.ErrorCode;

public class AlreadySolvedQuestionException extends BoardException {
    public AlreadySolvedQuestionException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AlreadySolvedQuestionException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
