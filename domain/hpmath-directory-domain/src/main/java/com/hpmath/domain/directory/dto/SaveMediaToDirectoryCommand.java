package com.hpmath.domain.directory.dto;

import com.hpmath.hpmathcore.Role;

public record SaveMediaToDirectoryCommand(
        String directoryPath,
        String mediaSrc,
        Long requestMemberId,
        Role requestMemberRole
) {
}
