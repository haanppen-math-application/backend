package com.hpmath.domain.board.exception;

import com.hpmath.hpmathcore.ErrorCode;

public class NoSuchMemberException extends BoardException {
    public NoSuchMemberException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchMemberException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
