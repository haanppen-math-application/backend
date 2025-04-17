package com.hpmath.hpmathcoreapi.course.application.exception;

import com.hpmath.hpmathcoreapi.exception.ErrorCode;

public class NotFoundTeacherException extends CourseException{
    public NotFoundTeacherException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotFoundTeacherException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
