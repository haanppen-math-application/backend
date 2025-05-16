package com.hpmath.domain.directory.dto;


import com.hpmath.hpmathcore.Role;

public record CommentDeleteCommand(
        Long commentId,
        Long requestMemberId,
        Role role
) {
}
