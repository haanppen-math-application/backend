package com.hpmath.domain.board.dto;


import com.hpmath.common.Role;

public record CommentDeleteCommand(
        Long commentId,
        Long requestMemberId,
        Role role
) {
}
