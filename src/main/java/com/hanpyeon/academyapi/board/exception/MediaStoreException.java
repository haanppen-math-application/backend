package com.hanpyeon.academyapi.board.exception;

import com.hanpyeon.academyapi.exception.ErrorCode;

public class MediaStoreException extends BoardException{
    public MediaStoreException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MediaStoreException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
