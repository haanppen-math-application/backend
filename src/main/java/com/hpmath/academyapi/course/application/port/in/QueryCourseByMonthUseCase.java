package com.hpmath.academyapi.course.application.port.in;

import com.hpmath.academyapi.course.controller.Responses.MemoAppliedDayResponse;
import java.time.LocalDate;
import java.util.List;

public interface QueryCourseByMonthUseCase {
    List<MemoAppliedDayResponse> query(final LocalDate registeredDAte, final Long studentId);
}
