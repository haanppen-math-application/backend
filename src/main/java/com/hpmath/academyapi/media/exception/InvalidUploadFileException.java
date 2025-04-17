package com.hpmath.academyapi.media.exception;

import com.hpmath.academyapi.exception.ErrorCode;

public class InvalidUploadFileException extends StorageException {
    public InvalidUploadFileException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidUploadFileException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
