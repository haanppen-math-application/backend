package com.hpmath.academyapi.course.application.port.out;

import com.hpmath.academyapi.course.domain.Memo;

public interface RegisterMemoPort {
    Long register(final Memo memo, final Long courseId);
}
