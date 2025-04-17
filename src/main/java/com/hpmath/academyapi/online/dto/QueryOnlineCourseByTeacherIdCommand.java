package com.hpmath.academyapi.online.dto;

import jakarta.validation.constraints.NotNull;

public record QueryOnlineCourseByTeacherIdCommand(
        @NotNull Long teacherId
) {
}
