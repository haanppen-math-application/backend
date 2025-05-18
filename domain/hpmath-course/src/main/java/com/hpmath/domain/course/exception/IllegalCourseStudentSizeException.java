package com.hpmath.domain.course.exception;

import com.hpmath.common.ErrorCode;

public class IllegalCourseStudentSizeException extends CourseException{
    public IllegalCourseStudentSizeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public IllegalCourseStudentSizeException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
