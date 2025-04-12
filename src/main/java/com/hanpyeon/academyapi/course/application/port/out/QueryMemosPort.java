package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.application.dto.MemoQueryCommand;
import com.hanpyeon.academyapi.course.controller.Responses.MemoViewResponse;
import org.springframework.data.domain.Page;

public interface QueryMemosPort {
    Page<MemoViewResponse> loadMemos(final MemoQueryCommand memoQueryCommand);
}
