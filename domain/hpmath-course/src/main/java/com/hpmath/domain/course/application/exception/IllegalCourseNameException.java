package com.hpmath.domain.course.application.exception;

import com.hpmath.hpmathcore.ErrorCode;

public class IllegalCourseNameException extends CourseException{
    public IllegalCourseNameException(ErrorCode errorCode) {
        super(errorCode);
    }

    public IllegalCourseNameException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
