package com.hpmath.domain.course.application.exception;

import com.hpmath.hpmathcore.ErrorCode;

public class NoSuchCourseException extends CourseException{
    public NoSuchCourseException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchCourseException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
