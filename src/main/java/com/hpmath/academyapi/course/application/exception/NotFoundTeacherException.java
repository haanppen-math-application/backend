package com.hpmath.academyapi.course.application.exception;

import com.hpmath.academyapi.exception.ErrorCode;

public class NotFoundTeacherException extends CourseException{
    public NotFoundTeacherException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotFoundTeacherException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
