package com.hpmath.domain.board.exception;

import com.hpmath.common.BusinessException;
import com.hpmath.common.ErrorCode;

public class BoardException extends BusinessException {
    public BoardException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BoardException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
