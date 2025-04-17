package com.hpmath.academyapi.course.application.port.out;

import com.hpmath.academyapi.course.domain.MemoMediaContainer;

public interface UpdateMemoMediaContainerPort {
    void save(final MemoMediaContainer memoMediaContainer);
}
