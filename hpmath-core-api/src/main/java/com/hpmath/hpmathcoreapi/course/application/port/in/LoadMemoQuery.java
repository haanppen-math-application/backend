package com.hpmath.hpmathcoreapi.course.application.port.in;

import com.hpmath.hpmathcoreapi.course.application.dto.MemoQueryCommand;
import com.hpmath.hpmathcoreapi.course.controller.Responses.MemoViewResponse;
import org.springframework.data.domain.Page;

public interface LoadMemoQuery {
    Page<MemoViewResponse> loadMemos(final MemoQueryCommand memoQueryCommand);
}
