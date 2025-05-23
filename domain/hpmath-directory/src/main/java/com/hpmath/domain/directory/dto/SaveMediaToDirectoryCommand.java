package com.hpmath.domain.directory.dto;

import com.hpmath.common.Role;
import com.hpmath.domain.directory.service.validation.DirectoryPathConstraint;
import jakarta.validation.constraints.NotNull;

public record SaveMediaToDirectoryCommand(
        @DirectoryPathConstraint
        String directoryPath,
        @NotNull
        String mediaSrc,
        @NotNull
        Long requestMemberId,
        @NotNull
        Role requestMemberRole
) {
}
