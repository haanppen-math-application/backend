package com.hpmath.hpmathcoreapi.dir.dto;

import com.hpmath.hpmathcoreapi.security.Role;

public record SaveMediaToDirectoryCommand(
        String directoryPath,
        String mediaSrc,
        Long requestMemberId,
        Role requestMemberRole
) {
}
