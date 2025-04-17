package com.hpmath.academyapi.board.exception;

import com.hpmath.academyapi.exception.ErrorCode;

public class NotAdoptedCommentException extends BoardException{
    public NotAdoptedCommentException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotAdoptedCommentException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
