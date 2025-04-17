package com.hpmath.academyapi.course.application.dto;

import com.hpmath.academyapi.security.Role;
import lombok.NonNull;

public record DeleteCourseCommand(
        @NonNull
        Long courseId,
        Role role,
        Long requestMemberId
) {
}
