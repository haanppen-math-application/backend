package com.hpmath.hpmathcoreapi.board.exception;

import com.hpmath.hpmathcore.ErrorCode;

public class NoSuchCommentException extends BoardException {
    public NoSuchCommentException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchCommentException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
