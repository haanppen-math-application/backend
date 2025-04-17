package com.hpmath.hpmathcoreapi.course.application.dto;

import com.hpmath.hpmathcoreapi.security.Role;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CourseRegisterCommand(
        @NotNull String courseName,
        @NotNull Long teacherId,
        List<Long> students,
        @NotNull Long requestMemberId,
        Role role
        ) {
}
