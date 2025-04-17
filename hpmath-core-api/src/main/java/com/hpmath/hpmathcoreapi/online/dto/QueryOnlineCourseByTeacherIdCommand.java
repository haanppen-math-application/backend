package com.hpmath.hpmathcoreapi.online.dto;

import jakarta.validation.constraints.NotNull;

public record QueryOnlineCourseByTeacherIdCommand(
        @NotNull Long teacherId
) {
}
