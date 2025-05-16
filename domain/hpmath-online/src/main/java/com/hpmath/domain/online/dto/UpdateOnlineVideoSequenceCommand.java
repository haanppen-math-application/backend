package com.hpmath.domain.online.dto;

import com.hpmath.hpmathcore.Role;
import jakarta.validation.constraints.NotNull;

public record UpdateOnlineVideoSequenceCommand(
        @NotNull Long targetCourseId,
        @NotNull Long targetVideoId,
        @NotNull Integer updatedSequence,
        @NotNull Long requestMemberId,
        @NotNull Role requetMemberRole
) {
}
