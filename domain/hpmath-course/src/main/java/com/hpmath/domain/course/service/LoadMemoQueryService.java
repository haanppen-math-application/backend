package com.hpmath.domain.course.service;

import com.hpmath.domain.course.dto.MemoQueryCommand;
import com.hpmath.domain.course.dto.Responses.MemoViewResponse;
import com.hpmath.domain.course.application.port.in.LoadMemoQuery;
import com.hpmath.domain.course.application.port.out.QueryMemosPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoadMemoQueryService implements LoadMemoQuery {

    private final QueryMemosPort queryMemosPort;

    @Override
    public Page<MemoViewResponse> loadMemos(final @Valid MemoQueryCommand command) {;
        return queryMemosPort.loadMemos(command);
    }
}
