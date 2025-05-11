package com.hpmath.hpmathcoreapi.course.application.port.out;

import com.hpmath.hpmathcoreapi.course.domain.Memo;

public interface LoadMemoPort {
    Memo loadMemo(final Long memoId);
}
