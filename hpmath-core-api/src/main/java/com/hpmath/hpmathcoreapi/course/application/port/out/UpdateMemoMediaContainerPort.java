package com.hpmath.hpmathcoreapi.course.application.port.out;

import com.hpmath.hpmathcoreapi.course.domain.MemoMediaContainer;

public interface UpdateMemoMediaContainerPort {
    void save(final MemoMediaContainer memoMediaContainer);
}
