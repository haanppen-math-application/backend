package com.hpmath.domain.online.dto;

import com.hpmath.hpmathcore.Role;
import jakarta.validation.constraints.NotNull;

public record OnlineLessonInitializeCommand(
        @NotNull Long onlineCourseId,
        @NotNull Long requetMemberId,
        @NotNull Role requetMemberROle
) {
}
