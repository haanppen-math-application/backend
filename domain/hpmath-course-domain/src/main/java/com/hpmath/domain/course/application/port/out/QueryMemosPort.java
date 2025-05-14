package com.hpmath.domain.course.application.port.out;

import com.hpmath.domain.course.application.dto.MemoQueryCommand;
import com.hpmath.domain.course.application.dto.Responses.MemoViewResponse;
import org.springframework.data.domain.Page;

public interface QueryMemosPort {
    Page<MemoViewResponse> loadMemos(final MemoQueryCommand memoQueryCommand);
}
