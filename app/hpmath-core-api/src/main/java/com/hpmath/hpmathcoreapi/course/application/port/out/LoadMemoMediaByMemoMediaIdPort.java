package com.hpmath.hpmathcoreapi.course.application.port.out;

import com.hpmath.hpmathcoreapi.course.domain.MemoMedia;

public interface LoadMemoMediaByMemoMediaIdPort {
    MemoMedia loadByMemoMediaId(final Long memoMediaId, final Long memoId);
}
