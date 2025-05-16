package com.hpmath.domain.media.exception;

import com.hpmath.common.ErrorCode;

public class InvalidUploadFileException extends StorageException {
    public InvalidUploadFileException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidUploadFileException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
