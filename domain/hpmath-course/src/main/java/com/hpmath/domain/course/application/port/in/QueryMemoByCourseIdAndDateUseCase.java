package com.hpmath.domain.course.application.port.in;

import com.hpmath.domain.course.dto.MemoQueryByCourseIdAndDateCommand;
import com.hpmath.domain.course.dto.Responses.MemoViewResponse;

public interface QueryMemoByCourseIdAndDateUseCase {
    MemoViewResponse loadSingleMemo(final MemoQueryByCourseIdAndDateCommand memoQueryByCourseIdAndDateCommand);
}
