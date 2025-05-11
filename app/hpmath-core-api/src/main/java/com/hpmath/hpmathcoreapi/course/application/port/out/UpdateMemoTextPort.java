package com.hpmath.hpmathcoreapi.course.application.port.out;

import com.hpmath.hpmathcoreapi.course.domain.Memo;

public interface UpdateMemoTextPort {
    void update(final Memo memo);
}
