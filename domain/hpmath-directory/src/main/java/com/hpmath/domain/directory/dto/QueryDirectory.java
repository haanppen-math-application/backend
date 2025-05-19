package com.hpmath.domain.directory.dto;

import com.hpmath.common.Role;
import com.hpmath.domain.directory.service.validation.DirectoryPathConstraint;
import jakarta.validation.constraints.NotNull;

public record QueryDirectory(
        @DirectoryPathConstraint
        String path,
        @NotNull
        Long requestMemberId,
        @NotNull
        Role role
) {
}
