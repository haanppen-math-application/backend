package com.hpmath.domain.directory.dto;

import com.hpmath.hpmathcore.Role;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CommentUpdateCommand(
        @NotNull Long requestMemberId,
        Role role,
        @NotNull Long commentId,
        String content,
        List<String> imageSources
) {
}
