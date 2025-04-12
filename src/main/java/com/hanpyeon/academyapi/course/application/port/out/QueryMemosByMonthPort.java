package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.controller.Responses.MemoAppliedDayResponse;
import java.time.LocalDate;
import java.util.List;

public interface QueryMemosByMonthPort {
    List<MemoAppliedDayResponse> query(final LocalDate startDate, final LocalDate endDate, final Long studentId);
}
