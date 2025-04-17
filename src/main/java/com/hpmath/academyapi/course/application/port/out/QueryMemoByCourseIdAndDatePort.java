package com.hpmath.academyapi.course.application.port.out;

import com.hpmath.academyapi.course.application.dto.MemoQueryByCourseIdAndDateCommand;
import com.hpmath.academyapi.course.controller.Responses.MemoViewResponse;

public interface QueryMemoByCourseIdAndDatePort {
    MemoViewResponse query(final MemoQueryByCourseIdAndDateCommand memoQueryByCourseIdAndDateCommand);
}
