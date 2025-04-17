package com.hpmath.academyapi.course.application.port.in;

import com.hpmath.academyapi.course.application.dto.MemoQueryCommand;
import com.hpmath.academyapi.course.controller.Responses.MemoViewResponse;
import org.springframework.data.domain.Page;

public interface LoadMemoQuery {
    Page<MemoViewResponse> loadMemos(final MemoQueryCommand memoQueryCommand);
}
