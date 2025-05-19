package com.hpmath.domain.directory.dto;

import com.hpmath.common.Role;
import com.hpmath.domain.directory.service.validation.DirectoryPathConstraint;
import jakarta.validation.constraints.NotNull;

public record DeleteDirectoryCommand(
        @DirectoryPathConstraint
        String targetPath,
        @NotNull
        Long requestMemberId,
        @NotNull
        Role requestMemberRole,
        @NotNull
        Boolean deleteChildes
) {
}
