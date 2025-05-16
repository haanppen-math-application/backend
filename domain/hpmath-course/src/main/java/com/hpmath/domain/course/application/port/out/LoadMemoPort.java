package com.hpmath.domain.course.application.port.out;

import com.hpmath.domain.course.domain.Memo;

public interface LoadMemoPort {
    Memo loadMemo(final Long memoId);
}
