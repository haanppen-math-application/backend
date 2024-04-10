package com.hanpyeon.academyapi.course.application.dto;

import java.time.LocalDateTime;
import java.util.List;

public record MemoRegisterCommand(
        Long requestMemberId,
        Long targetCourseId,
        String progressed,
        String homeWork,
        LocalDateTime registerTargetDateTime,
        List<Long> courseMediaIds,
        List<Long> conceptMediaIds
) {
}
