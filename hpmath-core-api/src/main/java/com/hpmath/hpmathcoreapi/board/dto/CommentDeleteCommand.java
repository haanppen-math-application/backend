package com.hpmath.hpmathcoreapi.board.dto;


import com.hpmath.hpmathcore.Role;

public record CommentDeleteCommand(
        Long commentId,
        Long requestMemberId,
        Role role
) {
}
