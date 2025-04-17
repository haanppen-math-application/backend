package com.hpmath.hpmathcoreapi.course.application.exception;

import com.hpmath.hpmathcoreapi.exception.BusinessException;
import com.hpmath.hpmathcoreapi.exception.ErrorCode;

public class CourseException extends BusinessException {
    public CourseException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CourseException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
