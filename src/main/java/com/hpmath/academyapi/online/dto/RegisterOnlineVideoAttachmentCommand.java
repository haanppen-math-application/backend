package com.hpmath.academyapi.online.dto;

import com.hpmath.academyapi.security.Role;
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
