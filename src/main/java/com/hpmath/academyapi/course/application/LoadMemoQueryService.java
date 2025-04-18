package com.hpmath.academyapi.course.application;

import com.hpmath.academyapi.course.application.dto.MemoQueryCommand;
import com.hpmath.academyapi.course.controller.Responses.MemoViewResponse;
import com.hpmath.academyapi.course.application.port.in.LoadMemoQuery;
import com.hpmath.academyapi.course.application.port.out.QueryMemosPort;
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
