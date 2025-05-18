package com.hpmath.domain.course.dto;

import jakarta.validation.constraints.NotNull;

public record CourseUpdateCommand(
        @NotNull Long courseId,
        @NotNull Long requestMemberId,
        String courseName,
        Long newTeacherId

) {
}
