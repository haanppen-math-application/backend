package com.hpmath.hpmathcoreapi.online.dto;

import com.hpmath.hpmathcoreapi.security.Role;
import jakarta.validation.constraints.NotNull;

public record UpdateOnlineVideoSequenceCommand(
        @NotNull Long targetCourseId,
        @NotNull Long targetVideoId,
        @NotNull Integer updatedSequence,
        @NotNull Long requestMemberId,
        @NotNull Role requetMemberRole
) {
}
