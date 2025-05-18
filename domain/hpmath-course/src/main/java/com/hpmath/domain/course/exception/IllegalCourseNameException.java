package com.hpmath.domain.course.exception;

import com.hpmath.common.ErrorCode;

public class IllegalCourseNameException extends CourseException{
    public IllegalCourseNameException(ErrorCode errorCode) {
        super(errorCode);
    }

    public IllegalCourseNameException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
