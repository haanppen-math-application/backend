package com.hpmath.academyapi.course.application.port.out;

import com.hpmath.academyapi.course.domain.Memo;

public interface LoadMemoPort {
    Memo loadMemo(final Long memoId);
}
