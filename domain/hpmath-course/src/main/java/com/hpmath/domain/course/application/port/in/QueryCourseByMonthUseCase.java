package com.hpmath.domain.course.application.port.in;

import com.hpmath.domain.course.dto.Responses.MemoAppliedDayResponse;
import java.time.LocalDate;
import java.util.List;

public interface QueryCourseByMonthUseCase {
    List<MemoAppliedDayResponse> query(final LocalDate registeredDAte, final Long studentId);
}
