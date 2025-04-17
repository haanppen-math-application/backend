package com.hpmath.academyapi.course.application.port.out;

import com.hpmath.academyapi.course.application.dto.MemoQueryCommand;
import com.hpmath.academyapi.course.controller.Responses.MemoViewResponse;
import org.springframework.data.domain.Page;

public interface QueryMemosPort {
    Page<MemoViewResponse> loadMemos(final MemoQueryCommand memoQueryCommand);
}
