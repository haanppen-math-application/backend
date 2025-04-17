package com.hpmath.academyapi.course.application.exception;

import com.hpmath.academyapi.exception.ErrorCode;

public class NoSuchMemberException extends CourseException{
    public NoSuchMemberException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchMemberException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
