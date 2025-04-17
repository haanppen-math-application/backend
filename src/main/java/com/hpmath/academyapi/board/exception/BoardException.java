package com.hpmath.academyapi.board.exception;

import com.hpmath.academyapi.exception.BusinessException;
import com.hpmath.academyapi.exception.ErrorCode;

public class BoardException extends BusinessException {
    public BoardException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BoardException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
