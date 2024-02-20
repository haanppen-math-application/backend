package com.hanpyeon.academyapi.media.exception;

import com.hanpyeon.academyapi.board.exception.BoardException;
import com.hanpyeon.academyapi.exception.ErrorCode;

public class NoSuchMediaException extends BoardException {
    public NoSuchMediaException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchMediaException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
