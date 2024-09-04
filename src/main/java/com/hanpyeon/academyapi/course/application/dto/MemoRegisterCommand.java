package com.hanpyeon.academyapi.course.application.dto;

import java.time.LocalDate;
import java.util.List;

public record MemoRegisterCommand(
        Long requestMemberId,
        Long targetCourseId,
        String progressed,
        String homeWork,
        LocalDate registerTargetDate,
        List<Long> courseMediaIds,
        List<Long> conceptMediaIds
) {
}
