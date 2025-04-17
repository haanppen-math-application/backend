package com.hpmath.hpmathcoreapi.course.application.port.out;

import com.hpmath.hpmathcoreapi.course.controller.Responses.MemoAppliedDayResponse;
import java.time.LocalDate;
import java.util.List;

public interface QueryMemosByMonthPort {
    List<MemoAppliedDayResponse> query(final LocalDate startDate, final LocalDate endDate, final Long studentId);
}
