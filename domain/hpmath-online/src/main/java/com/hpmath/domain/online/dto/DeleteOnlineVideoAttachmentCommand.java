package com.hpmath.domain.online.dto;

import com.hpmath.common.Role;
import jakarta.validation.constraints.NotNull;

public record DeleteOnlineVideoAttachmentCommand(
        @NotNull Long attachmentId,
        @NotNull Long onlineVideoId,
        @NotNull Long requestMemberId,
        @NotNull Role requestMemberRole
) {
}
