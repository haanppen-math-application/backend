package com.hpmath.academyapi.course.application.exception;

import com.hpmath.academyapi.exception.ErrorCode;

public class NoSuchCourseException extends CourseException{
    public NoSuchCourseException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchCourseException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
