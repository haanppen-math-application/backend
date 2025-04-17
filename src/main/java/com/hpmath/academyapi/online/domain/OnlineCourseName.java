package com.hpmath.academyapi.online.domain;

import com.hpmath.academyapi.exception.BusinessException;
import com.hpmath.academyapi.exception.ErrorCode;
import java.util.Objects;

public record OnlineCourseName(String name) {
    private static final int COURSE_NAME_LENGTH = 10;
    public OnlineCourseName {
        validate(name);
    }

    private void validate(final String name) {
        if (Objects.isNull(name)) {
            throw new BusinessException(ErrorCode.BANNER_EXCEPTION);
        }
        if (name.length() > COURSE_NAME_LENGTH) {
            throw new BusinessException(ErrorCode.ONLINE_COURSE_EXCEPTION);
        }
    }
}
