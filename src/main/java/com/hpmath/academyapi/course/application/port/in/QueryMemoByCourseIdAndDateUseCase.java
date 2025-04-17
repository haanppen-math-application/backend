package com.hpmath.academyapi.course.application.port.in;

import com.hpmath.academyapi.course.application.dto.MemoQueryByCourseIdAndDateCommand;
import com.hpmath.academyapi.course.controller.Responses.MemoViewResponse;

public interface QueryMemoByCourseIdAndDateUseCase {
    MemoViewResponse loadSingleMemo(final MemoQueryByCourseIdAndDateCommand memoQueryByCourseIdAndDateCommand);
}
