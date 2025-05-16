package com.hpmath.domain.course.application.exception;

import com.hpmath.hpmathcore.ErrorCode;

public class InvalidCourseAccessException extends CourseException{
    public InvalidCourseAccessException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidCourseAccessException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
