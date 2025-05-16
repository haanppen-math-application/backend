package com.hpmath.domain.course.application.exception;

import com.hpmath.common.ErrorCode;

public class NoSuchMemberException extends CourseException{
    public NoSuchMemberException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchMemberException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
