package com.hpmath.domain.course.application.exception;

import com.hpmath.hpmathcore.ErrorCode;

public class IllegalCourseStudentSizeException extends CourseException{
    public IllegalCourseStudentSizeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public IllegalCourseStudentSizeException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
