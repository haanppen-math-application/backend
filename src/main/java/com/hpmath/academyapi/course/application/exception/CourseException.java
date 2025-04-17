package com.hpmath.academyapi.course.application.exception;

import com.hpmath.academyapi.exception.BusinessException;
import com.hpmath.academyapi.exception.ErrorCode;

public class CourseException extends BusinessException {
    public CourseException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CourseException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
