package com.hpmath.hpmathcoreapi.course.application.exception;

import com.hpmath.hpmathcore.BusinessException;
import com.hpmath.hpmathcore.ErrorCode;

public class CourseException extends BusinessException {
    public CourseException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CourseException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
