package com.hanpyeon.academyapi.online.dto;

import com.hanpyeon.academyapi.security.Role;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record AddOnlineCourseRequest(
        @NotNull String courseName,
        @NotNull Long teacherId,
        List<Long> students
) {
    public AddOnlineCourseCommand toCommand(final Long requestMemberId, final Role role) {
        return new AddOnlineCourseCommand(requestMemberId, role, this.courseName, this.students, this.teacherId);
    }
}