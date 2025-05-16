package com.hpmath.domain.course.application.port.in;

import com.hpmath.domain.course.application.dto.MemoQueryByCourseIdAndDateCommand;
import com.hpmath.domain.course.application.dto.Responses.MemoViewResponse;

public interface QueryMemoByCourseIdAndDateUseCase {
    MemoViewResponse loadSingleMemo(final MemoQueryByCourseIdAndDateCommand memoQueryByCourseIdAndDateCommand);
}
