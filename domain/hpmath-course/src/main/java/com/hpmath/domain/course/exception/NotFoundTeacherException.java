package com.hpmath.domain.course.exception;

import com.hpmath.common.ErrorCode;

public class NotFoundTeacherException extends CourseException{
    public NotFoundTeacherException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotFoundTeacherException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
