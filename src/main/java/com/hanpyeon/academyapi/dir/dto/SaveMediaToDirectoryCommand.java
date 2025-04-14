package com.hanpyeon.academyapi.dir.dto;

import com.hanpyeon.academyapi.security.Role;

public record SaveMediaToDirectoryCommand(
        String directoryPath,
        String mediaSrc,
        Long requestMemberId,
        Role requestMemberRole
) {
}
