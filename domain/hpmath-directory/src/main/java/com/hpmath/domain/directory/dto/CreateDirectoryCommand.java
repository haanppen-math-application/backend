package com.hpmath.domain.directory.dto;

import com.hpmath.domain.directory.service.validation.DirectoryNameConstraint;
import com.hpmath.domain.directory.service.validation.DirectoryPathConstraint;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;

public record CreateDirectoryCommand(
        @DirectoryNameConstraint
        String directoryName,
        @DirectoryPathConstraint
        String parentDirPath,
        @NotNull
        Long ownerId,
        @NotNull
        Boolean canViewByEveryone,
        @NotNull
        Boolean canModifyByEveryone
) {
}
