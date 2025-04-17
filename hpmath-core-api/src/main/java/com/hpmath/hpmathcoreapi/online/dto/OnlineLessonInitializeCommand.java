package com.hpmath.hpmathcoreapi.online.dto;

import com.hpmath.hpmathcoreapi.security.Role;
import jakarta.validation.constraints.NotNull;

public record OnlineLessonInitializeCommand(
        @NotNull Long onlineCourseId,
        @NotNull Long requetMemberId,
        @NotNull Role requetMemberROle
) {
}
