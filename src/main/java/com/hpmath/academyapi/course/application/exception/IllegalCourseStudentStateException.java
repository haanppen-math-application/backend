package com.hpmath.academyapi.course.application.exception;

import com.hpmath.academyapi.exception.ErrorCode;

public class IllegalCourseStudentStateException extends CourseException {
    public IllegalCourseStudentStateException(ErrorCode errorCode) {
        super(errorCode);
    }

    public IllegalCourseStudentStateException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
