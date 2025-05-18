package com.hpmath.domain.course.application.port.out;

import com.hpmath.domain.course.dto.MemoQueryByCourseIdAndDateCommand;
import com.hpmath.domain.course.dto.Responses.MemoViewResponse;

public interface QueryMemoByCourseIdAndDatePort {
    MemoViewResponse query(final MemoQueryByCourseIdAndDateCommand memoQueryByCourseIdAndDateCommand);
}
