package com.hpmath.hpmathcoreapi.course.application.exception;

import com.hpmath.hpmathcoreapi.exception.ErrorCode;

public class IllegalCourseStudentStateException extends CourseException {
    public IllegalCourseStudentStateException(ErrorCode errorCode) {
        super(errorCode);
    }

    public IllegalCourseStudentStateException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
