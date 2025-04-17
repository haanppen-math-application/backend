package com.hpmath.hpmathcoreapi.online.dto;

import com.hpmath.hpmathcoreapi.security.Role;
import jakarta.validation.constraints.NotBlank;

public record RegisterOnlineVideoAttachmentRequest(
        @NotBlank String attachmentTitle,
        @NotBlank String attachmentContent
) {
    public RegisterOnlineVideoAttachmentCommand toCommand(
            final Long onlineCourseId,
            final Long onlineVideoId,
            final Long requestMemberId,
            final Role requstMemberRole
    ) {
        return new RegisterOnlineVideoAttachmentCommand(attachmentTitle, attachmentContent, onlineCourseId, onlineVideoId, requestMemberId, requstMemberRole);
    }
}
