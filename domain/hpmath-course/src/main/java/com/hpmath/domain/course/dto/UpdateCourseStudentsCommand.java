package com.hpmath.domain.course.dto;

import java.util.List;

public record UpdateCourseStudentsCommand(Long requestMemberId, Long courseId, List<Long> studentIds) {
    public static UpdateCourseStudentsCommand of(final Long requestMemberId, final Long courseId, final List<Long> studentIds) {
        return new UpdateCourseStudentsCommand(requestMemberId, courseId, studentIds);
    }
}
