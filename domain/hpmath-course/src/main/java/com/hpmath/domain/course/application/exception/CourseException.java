package com.hpmath.domain.course.application.exception;

import com.hpmath.common.BusinessException;
import com.hpmath.common.ErrorCode;

public class CourseException extends BusinessException {
    public CourseException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CourseException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
