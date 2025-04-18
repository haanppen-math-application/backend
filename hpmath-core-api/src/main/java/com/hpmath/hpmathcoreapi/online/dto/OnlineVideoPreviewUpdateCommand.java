package com.hpmath.hpmathcoreapi.online.dto;

import com.hpmath.hpmathcore.Role;
import jakarta.validation.constraints.NotNull;

public record OnlineVideoPreviewUpdateCommand(
        @NotNull Long onlineVideoId,
        @NotNull Boolean previewStatus,
        @NotNull Long requestMemerId,
        @NotNull Role requetMemberRole
) {
}
