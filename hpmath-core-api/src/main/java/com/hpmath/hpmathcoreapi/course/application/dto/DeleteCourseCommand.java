package com.hpmath.hpmathcoreapi.course.application.dto;

import com.hpmath.hpmathcoreapi.security.Role;
import lombok.NonNull;

public record DeleteCourseCommand(
        @NonNull
        Long courseId,
        Role role,
        Long requestMemberId
) {
}
