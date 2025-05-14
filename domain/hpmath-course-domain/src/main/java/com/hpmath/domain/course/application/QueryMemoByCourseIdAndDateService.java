package com.hpmath.domain.course.application;

import com.hpmath.domain.course.application.dto.MemoQueryByCourseIdAndDateCommand;
import com.hpmath.domain.course.application.dto.Responses.MemoViewResponse;
import com.hpmath.domain.course.application.port.in.QueryMemoByCourseIdAndDateUseCase;
import com.hpmath.domain.course.application.port.out.QueryMemoByCourseIdAndDatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryMemoByCourseIdAndDateService implements QueryMemoByCourseIdAndDateUseCase {

    private final QueryMemoByCourseIdAndDatePort queryMemoByCourseIdAndDatePort;
    @Override
    public MemoViewResponse loadSingleMemo(MemoQueryByCourseIdAndDateCommand memoQueryByCourseIdAndDateCommand) {
        return queryMemoByCourseIdAndDatePort.query(memoQueryByCourseIdAndDateCommand);
    }
}
