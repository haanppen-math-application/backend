package com.hpmath.academyapi.online.dto;

import com.hpmath.academyapi.security.Role;
import jakarta.validation.constraints.NotNull;

public record ChangeOnlineCourseInfoCommand(
        @NotNull
        Long onlineCourseId,
        String onlineCourseTitle,
        String onlineCourseRange,
        String onlineCourseContent,
        Long categoryId,
        Long requestMemberId,
        Role requestMemberRole
) {
}
