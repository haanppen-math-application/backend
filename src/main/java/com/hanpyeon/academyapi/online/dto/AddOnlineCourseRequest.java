package com.hanpyeon.academyapi.online.dto;

import com.hanpyeon.academyapi.security.Role;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record AddOnlineCourseRequest(
        @NotNull String onlineCourseName,
        @NotNull Long teacherId,
        List<Long> studentIds
) {
    public AddOnlineCourseCommand toCommand(final Long requestMemberId, final Role role) {
        return new AddOnlineCourseCommand(requestMemberId, role, this.onlineCourseName, this.studentIds, this.teacherId);
    }
}