package com.hpmath.domain.course.application.port.out;

import com.hpmath.domain.course.domain.Memo;

public interface RegisterMemoPort {
    Long register(final Memo memo, final Long courseId);
}
