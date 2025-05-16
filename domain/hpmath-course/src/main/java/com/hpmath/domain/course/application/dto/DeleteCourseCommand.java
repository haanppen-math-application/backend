package com.hpmath.domain.course.application.dto;

import com.hpmath.common.Role;
import lombok.NonNull;

public record DeleteCourseCommand(
        @NonNull
        Long courseId,
        Role role,
        Long requestMemberId
) {
}
