package com.hpmath.domain.course.exception;

import com.hpmath.common.ErrorCode;

public class NoSuchCourseException extends CourseException{
    public NoSuchCourseException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchCourseException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
