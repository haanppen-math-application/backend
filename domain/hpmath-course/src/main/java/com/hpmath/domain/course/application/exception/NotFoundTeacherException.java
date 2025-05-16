package com.hpmath.domain.course.application.exception;

import com.hpmath.hpmathcore.ErrorCode;

public class NotFoundTeacherException extends CourseException{
    public NotFoundTeacherException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotFoundTeacherException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
