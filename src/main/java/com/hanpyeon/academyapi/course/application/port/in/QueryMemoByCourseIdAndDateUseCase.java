package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.MemoQueryByCourseIdAndDateCommand;
import com.hanpyeon.academyapi.course.controller.Responses.MemoViewResponse;

public interface QueryMemoByCourseIdAndDateUseCase {
    MemoViewResponse loadSingleMemo(final MemoQueryByCourseIdAndDateCommand memoQueryByCourseIdAndDateCommand);
}
