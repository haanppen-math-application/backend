package com.hpmath.academyapi.online.dto;

import com.hpmath.academyapi.security.Role;
import jakarta.validation.constraints.NotNull;

public record OnlineLessonInitializeCommand(
        @NotNull Long onlineCourseId,
        @NotNull Long requetMemberId,
        @NotNull Role requetMemberROle
) {
}
