package com.hpmath.domain.directory.dto;

import com.hpmath.domain.directory.dao.Directory;
import com.hpmath.domain.directory.service.validation.DirectoryNameConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateDirectoryCommand(
        Directory directory,
        @DirectoryNameConstraint
        String newDirName,
        @NotNull
        Long requestMemberId
) {
}
