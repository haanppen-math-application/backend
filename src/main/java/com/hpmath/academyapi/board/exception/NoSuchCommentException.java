package com.hpmath.academyapi.board.exception;

import com.hpmath.academyapi.exception.ErrorCode;

public class NoSuchCommentException extends BoardException {
    public NoSuchCommentException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchCommentException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
