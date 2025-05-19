package com.hpmath.domain.board.comment.exception;

import com.hpmath.common.BusinessException;
import com.hpmath.common.ErrorCode;

public class CommentException extends BusinessException {
    public CommentException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }

    public CommentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
