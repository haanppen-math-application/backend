package com.hpmath.domain.board.exception;

import com.hpmath.hpmathcore.ErrorCode;

public class NotAllowedCommentException extends BoardException{
    public NotAllowedCommentException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotAllowedCommentException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
