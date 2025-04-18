package com.hpmath.hpmathcoreapi.course.application.exception;

import com.hpmath.hpmathcore.ErrorCode;

public class NoSuchMemberException extends CourseException{
    public NoSuchMemberException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchMemberException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
