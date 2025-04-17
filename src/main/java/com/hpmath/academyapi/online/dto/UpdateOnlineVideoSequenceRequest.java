package com.hpmath.academyapi.online.dto;

import com.hpmath.academyapi.security.Role;
import jakarta.validation.constraints.NotNull;

public record UpdateOnlineVideoSequenceRequest(
        @NotNull Long onlineCourseId,
        @NotNull Long targetVideoId,
        @NotNull Integer updatedSequence
) {
    public UpdateOnlineVideoSequenceCommand toCommand(final Long requestMemberId, final Role requestMemberRole) {
        return new UpdateOnlineVideoSequenceCommand(onlineCourseId, targetVideoId, updatedSequence, requestMemberId, requestMemberRole);
    }
}
