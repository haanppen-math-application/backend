package com.hpmath.hpmathcoreapi.course.application.exception;

import com.hpmath.hpmathcoreapi.exception.ErrorCode;

public class NoSuchCourseException extends CourseException{
    public NoSuchCourseException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchCourseException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
