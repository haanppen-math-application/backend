package com.hpmath.domain.course.application.port.in;

import com.hpmath.domain.course.application.dto.MemoQueryCommand;
import com.hpmath.domain.course.application.dto.Responses.MemoViewResponse;
import org.springframework.data.domain.Page;

public interface LoadMemoQuery {
    Page<MemoViewResponse> loadMemos(final MemoQueryCommand memoQueryCommand);
}
