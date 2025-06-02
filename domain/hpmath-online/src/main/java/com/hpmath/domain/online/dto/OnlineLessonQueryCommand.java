package com.hpmath.domain.online.dto;


import com.hpmath.common.Role;
import jakarta.validation.constraints.NotNull;

public record OnlineLessonQueryCommand(
        @NotNull
        Long courseId,
        @NotNull
        Long requestMemberId,
        @NotNull
        Role requestMemberRole
) {
}
