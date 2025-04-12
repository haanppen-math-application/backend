package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.controller.Responses.MemoAppliedDayResponse;
import java.time.LocalDate;
import java.util.List;

public interface QueryCourseByMonthUseCase {
    List<MemoAppliedDayResponse> query(final LocalDate registeredDAte, final Long studentId);
}
