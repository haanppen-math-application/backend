package com.hpmath.domain.board.exception;

import com.hpmath.common.ErrorCode;

public class NoSuchCommentException extends BoardException {
    public NoSuchCommentException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchCommentException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
