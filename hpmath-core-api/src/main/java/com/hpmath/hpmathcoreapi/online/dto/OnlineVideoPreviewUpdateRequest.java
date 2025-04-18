package com.hpmath.hpmathcoreapi.online.dto;

import com.hpmath.hpmathcore.Role;
import jakarta.validation.constraints.NotNull;

public record OnlineVideoPreviewUpdateRequest(
        @NotNull Long onlineVideoId,
        @NotNull Boolean previewStatus
) {
    public OnlineVideoPreviewUpdateCommand toCommand(final Long requestMemberId, final Role requestMemberRole) {
        return new OnlineVideoPreviewUpdateCommand(onlineVideoId, previewStatus, requestMemberId, requestMemberRole);
    }
}
