package com.hanpyeon.academyapi.board.dto;

import com.hanpyeon.academyapi.security.Role;

public record CommentDeleteCommand(
        Long commentId,
        Long requestMemberId,
        Role role
) {
}
