package com.hpmath.academyapi.course.application;

import com.hpmath.academyapi.course.controller.Responses.MemoAppliedDayResponse;
import com.hpmath.academyapi.course.application.port.in.QueryCourseByMonthUseCase;
import com.hpmath.academyapi.course.application.port.out.QueryMemosByMonthPort;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QueryCourseByMonthService implements QueryCourseByMonthUseCase {
    private final QueryMemosByMonthPort queryMemosByMonthPort;

    @Override
    public List<MemoAppliedDayResponse> query(final LocalDate registeredDate, final Long studentId) {
        final LocalDate startDate = registeredDate.withDayOfMonth(1);
        final LocalDate endDate = registeredDate.withDayOfMonth(registeredDate.lengthOfMonth());

        return queryMemosByMonthPort.query(startDate, endDate, studentId);
    }
}
