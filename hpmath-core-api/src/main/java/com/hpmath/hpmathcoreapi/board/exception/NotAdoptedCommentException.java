package com.hpmath.hpmathcoreapi.board.exception;

import com.hpmath.hpmathcoreapi.exception.ErrorCode;

public class NotAdoptedCommentException extends BoardException{
    public NotAdoptedCommentException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotAdoptedCommentException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
