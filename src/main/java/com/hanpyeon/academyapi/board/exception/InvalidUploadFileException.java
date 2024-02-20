package com.hanpyeon.academyapi.board.exception;

import com.hanpyeon.academyapi.exception.ErrorCode;

public class InvalidUploadFileException extends BoardException {
    public InvalidUploadFileException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidUploadFileException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
