package com.hpmath.domain.course.dto;

import com.hpmath.domain.course.service.validation.CourseStudentSizeConstraint;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record UpdateCourseStudentsCommand(
        @NotNull
        Long requestMemberId,
        @NotNull
        Long courseId,
        @CourseStudentSizeConstraint
        List<Long> studentIds
) {
    public static UpdateCourseStudentsCommand of(final Long requestMemberId, final Long courseId, final List<Long> studentIds) {
        return new UpdateCourseStudentsCommand(requestMemberId, courseId, studentIds);
    }
}
