package com.hpmath.hpmathcoreapi.course.application.port.out;

import com.hpmath.hpmathcoreapi.course.domain.Memo;

public interface RegisterMemoPort {
    Long register(final Memo memo, final Long courseId);
}
