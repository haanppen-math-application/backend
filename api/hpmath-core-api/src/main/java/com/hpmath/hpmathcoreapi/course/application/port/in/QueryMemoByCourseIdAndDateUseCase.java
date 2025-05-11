package com.hpmath.hpmathcoreapi.course.application.port.in;

import com.hpmath.hpmathcoreapi.course.application.dto.MemoQueryByCourseIdAndDateCommand;
import com.hpmath.hpmathcoreapi.course.controller.Responses.MemoViewResponse;

public interface QueryMemoByCourseIdAndDateUseCase {
    MemoViewResponse loadSingleMemo(final MemoQueryByCourseIdAndDateCommand memoQueryByCourseIdAndDateCommand);
}
