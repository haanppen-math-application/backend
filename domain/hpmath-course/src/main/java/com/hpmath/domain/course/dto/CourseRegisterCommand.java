package com.hpmath.domain.course.dto;

import com.hpmath.common.Role;
import com.hpmath.domain.course.service.validation.CourseNameConstraint;
import com.hpmath.domain.course.service.validation.CourseStudentSizeConstraint;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CourseRegisterCommand(
        @CourseNameConstraint
        String courseName,
        @NotNull
        Long teacherId,
        @CourseStudentSizeConstraint
        List<Long> students,
        @NotNull
        Long requestMemberId,
        @NotNull
        Role role
) {
}
