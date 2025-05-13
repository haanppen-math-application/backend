package com.hpmath.domain.online.dto;

import jakarta.validation.constraints.NotNull;

public record QueryOnlineCourseByTeacherIdCommand(
        @NotNull Long teacherId
) {
}
