package com.hpmath.hpmathcoreapi.online.dto;

import jakarta.validation.constraints.NotNull;

public record QueryOnlineCourseByStudentIdCommand(
        @NotNull Long studentId
) {
}
