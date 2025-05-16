package com.hpmath.domain.course.application.port.out;

import com.hpmath.domain.course.domain.MemoMedia;

public interface LoadMemoMediaByMemoMediaIdPort {
    MemoMedia loadByMemoMediaId(final Long memoMediaId, final Long memoId);
}
