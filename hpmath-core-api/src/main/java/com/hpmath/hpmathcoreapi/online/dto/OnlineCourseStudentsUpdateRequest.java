package com.hpmath.hpmathcoreapi.online.dto;

import java.util.List;

public record OnlineCourseStudentsUpdateRequest(
        List<Long> studentIds
) {
    public OnlineCourseStudentUpdateCommand toCommand(final Long requestMemberId, final Long courseId) {
        return new OnlineCourseStudentUpdateCommand(requestMemberId, courseId, studentIds);
    }
}
