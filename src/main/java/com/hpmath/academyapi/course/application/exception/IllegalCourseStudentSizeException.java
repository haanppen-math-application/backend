package com.hpmath.academyapi.course.application.exception;

import com.hpmath.academyapi.exception.ErrorCode;

public class IllegalCourseStudentSizeException extends CourseException{
    public IllegalCourseStudentSizeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public IllegalCourseStudentSizeException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
