package com.hpmath.domain.online.dto;

import com.hpmath.common.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddOnlineVideoCommand(
        @NotNull Long onlineCourseId,
        @Valid OnlineVideoCommand onlineVideoCommand,
        @NotNull Long requestMemberId,
        @NotNull Role requestMemberRole
) {
    public record OnlineVideoCommand(
            @NotNull
            String videoSrc,
            @NotNull
            Boolean isPreview
    ) {
    }
}
