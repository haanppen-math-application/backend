package com.hpmath.academyapi.board.dto;

import com.hpmath.academyapi.security.Role;
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
