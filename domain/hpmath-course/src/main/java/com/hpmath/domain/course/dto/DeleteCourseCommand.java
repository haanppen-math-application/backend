package com.hpmath.domain.course.dto;

import com.hpmath.common.Role;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

public record DeleteCourseCommand(
        @NotNull
        Long courseId,
        @NotNull
        Role role,
        @NotNull
        Long requestMemberId
) {
}
