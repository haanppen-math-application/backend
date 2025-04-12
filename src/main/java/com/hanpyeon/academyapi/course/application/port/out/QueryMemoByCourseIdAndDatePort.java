package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.application.dto.MemoQueryByCourseIdAndDateCommand;
import com.hanpyeon.academyapi.course.controller.Responses.MemoViewResponse;

public interface QueryMemoByCourseIdAndDatePort {
    MemoViewResponse query(final MemoQueryByCourseIdAndDateCommand memoQueryByCourseIdAndDateCommand);
}
