package com.hpmath.hpmathcoreapi.board.exception;

import com.hpmath.hpmathcoreapi.exception.BusinessException;
import com.hpmath.hpmathcoreapi.exception.ErrorCode;

public class BoardException extends BusinessException {
    public BoardException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BoardException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
