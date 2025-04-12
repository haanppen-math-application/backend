package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.application.dto.MemoQueryByCourseIdAndDateCommand;
import com.hanpyeon.academyapi.course.controller.Responses.MemoViewResponse;
import com.hanpyeon.academyapi.course.application.port.in.QueryMemoByCourseIdAndDateUseCase;
import com.hanpyeon.academyapi.course.application.port.out.QueryMemoByCourseIdAndDatePort;
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
