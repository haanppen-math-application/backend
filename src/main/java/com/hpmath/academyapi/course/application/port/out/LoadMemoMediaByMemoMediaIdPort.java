package com.hpmath.academyapi.course.application.port.out;

import com.hpmath.academyapi.course.domain.MemoMedia;

public interface LoadMemoMediaByMemoMediaIdPort {
    MemoMedia loadByMemoMediaId(final Long memoMediaId, final Long memoId);
}
