package com.hpmath.domain.board.dto;

import com.hpmath.hpmathcore.Role;

public record SaveMediaToDirectoryCommand(
        String directoryPath,
        String mediaSrc,
        Long requestMemberId,
        Role requestMemberRole
) {
}
