package com.hpmath.academyapi.online.dto;

import com.hpmath.academyapi.security.Role;
import jakarta.validation.constraints.NotNull;

public record UpdateOnlineVideoSequenceCommand(
        @NotNull Long targetCourseId,
        @NotNull Long targetVideoId,
        @NotNull Integer updatedSequence,
        @NotNull Long requestMemberId,
        @NotNull Role requetMemberRole
) {
}
