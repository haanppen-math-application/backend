package com.hpmath.domain.course.application.port.in;

import com.hpmath.domain.course.dto.MemoQueryCommand;
import com.hpmath.domain.course.dto.Responses.MemoViewResponse;
import org.springframework.data.domain.Page;

public interface LoadMemoQuery {
    Page<MemoViewResponse> loadMemos(final MemoQueryCommand memoQueryCommand);
}
