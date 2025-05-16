package com.hpmath.domain.online.domain;

import com.hpmath.common.BusinessException;
import com.hpmath.common.ErrorCode;
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
