package com.hpmath.academyapi.board.exception;

import com.hpmath.academyapi.exception.ErrorCode;

public class NoSuchMemberException extends BoardException {
    public NoSuchMemberException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchMemberException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
