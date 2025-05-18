package com.hpmath.domain.course.exception;

import com.hpmath.common.ErrorCode;

public class InvalidCourseAccessException extends CourseException{
    public InvalidCourseAccessException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidCourseAccessException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
