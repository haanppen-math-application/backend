package com.hpmath.domain.online.dto;

import com.hpmath.common.Role;
import jakarta.validation.constraints.NotNull;

public record OnlineVideoPreviewUpdateCommand(
        @NotNull Long onlineVideoId,
        @NotNull Boolean previewStatus,
        @NotNull Long requestMemerId,
        @NotNull Role requetMemberRole
) {
}
