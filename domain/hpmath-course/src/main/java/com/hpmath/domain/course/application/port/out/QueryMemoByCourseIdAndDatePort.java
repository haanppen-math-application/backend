package com.hpmath.domain.course.application.port.out;

import com.hpmath.domain.course.application.dto.MemoQueryByCourseIdAndDateCommand;
import com.hpmath.domain.course.application.dto.Responses.MemoViewResponse;

public interface QueryMemoByCourseIdAndDatePort {
    MemoViewResponse query(final MemoQueryByCourseIdAndDateCommand memoQueryByCourseIdAndDateCommand);
}
