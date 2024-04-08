package com.hanpyeon.academyapi.course.application.dto;

import java.util.List;

public record MemoRegisterCommand(
        Long requestMemberId,
        Long targetCourseId,
        String progressed,
        String homeWork,
        List<Long> courseMediaIds,
        List<Long> conceptMediaIds
) {
}
