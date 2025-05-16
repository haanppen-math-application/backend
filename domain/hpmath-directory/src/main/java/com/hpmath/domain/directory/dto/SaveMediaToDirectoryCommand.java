package com.hpmath.domain.directory.dto;

import com.hpmath.common.Role;

public record SaveMediaToDirectoryCommand(
        String directoryPath,
        String mediaSrc,
        Long requestMemberId,
        Role requestMemberRole
) {
}
