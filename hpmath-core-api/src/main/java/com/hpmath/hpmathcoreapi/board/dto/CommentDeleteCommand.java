package com.hpmath.hpmathcoreapi.board.dto;

import com.hpmath.hpmathcoreapi.security.Role;

public record CommentDeleteCommand(
        Long commentId,
        Long requestMemberId,
        Role role
) {
}
