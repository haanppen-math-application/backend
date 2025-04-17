package com.hpmath.academyapi.course.application.exception;

import com.hpmath.academyapi.exception.ErrorCode;

public class InvalidCourseAccessException extends CourseException{
    public InvalidCourseAccessException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidCourseAccessException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
