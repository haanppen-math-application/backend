package com.hpmath.hpmathcoreapi.board.exception;

import com.hpmath.hpmathcoreapi.exception.ErrorCode;

public class NotAllowedCommentException extends BoardException{
    public NotAllowedCommentException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotAllowedCommentException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
