package com.hpmath.academyapi.board.dto;

import com.hpmath.academyapi.security.Role;

public record CommentDeleteCommand(
        Long commentId,
        Long requestMemberId,
        Role role
) {
}
