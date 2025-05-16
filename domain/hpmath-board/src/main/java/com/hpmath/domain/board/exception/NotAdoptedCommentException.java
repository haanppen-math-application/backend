package com.hpmath.domain.board.exception;

import com.hpmath.common.ErrorCode;

public class NotAdoptedCommentException extends BoardException {
    public NotAdoptedCommentException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotAdoptedCommentException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
