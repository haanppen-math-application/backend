package com.hpmath.hpmathcoreapi.online.dto;

import com.hpmath.hpmathcore.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterOnlineVideoAttachmentCommand(
        @NotBlank String attachmentTitle,
        @NotBlank String attachmentContent,
        @NotNull Long onlineCourseId,
        @NotNull Long onlineVideoId,
        @NotNull Long requestMemberId,
        @NotNull Role requestMemberRole
) {
}
