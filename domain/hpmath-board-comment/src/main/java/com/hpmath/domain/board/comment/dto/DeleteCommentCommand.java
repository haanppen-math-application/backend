package com.hpmath.domain.board.comment.dto;

import com.hpmath.common.Role;
import jakarta.validation.constraints.NotNull;

public record DeleteCommentCommand(
        @NotNull
        Long commentId,
        @NotNull
        Long requestMemberId,
        @NotNull
        Role role
) {
}
