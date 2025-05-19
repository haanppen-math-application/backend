package com.hpmath.domain.board.comment.dto;

import com.hpmath.common.Role;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record UpdateCommentCommand(
        @NotNull
        Long requestMemberId,
        @NotNull
        Role role,
        @NotNull
        Long commentId,
        String content,
        @NotNull
        List<String> imageSources
) {
}
