package com.hpmath.academyapi.online.dto;

import com.hpmath.academyapi.security.Role;
import jakarta.validation.constraints.NotNull;

public record DeleteOnlineCourseVideoCommand(
        @NotNull Long onlineCourseId,
        @NotNull Long onlineVideoId,
        @NotNull Long requestMemberId,
        @NotNull Role requestMemberRole
) {
}
