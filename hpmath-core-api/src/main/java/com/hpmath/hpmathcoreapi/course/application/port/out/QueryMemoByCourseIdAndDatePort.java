package com.hpmath.hpmathcoreapi.course.application.port.out;

import com.hpmath.hpmathcoreapi.course.application.dto.MemoQueryByCourseIdAndDateCommand;
import com.hpmath.hpmathcoreapi.course.controller.Responses.MemoViewResponse;

public interface QueryMemoByCourseIdAndDatePort {
    MemoViewResponse query(final MemoQueryByCourseIdAndDateCommand memoQueryByCourseIdAndDateCommand);
}
