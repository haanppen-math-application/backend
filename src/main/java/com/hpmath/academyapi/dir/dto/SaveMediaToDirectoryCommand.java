package com.hpmath.academyapi.dir.dto;

import com.hpmath.academyapi.security.Role;

public record SaveMediaToDirectoryCommand(
        String directoryPath,
        String mediaSrc,
        Long requestMemberId,
        Role requestMemberRole
) {
}
