package com.hpmath.domain.directory.dto;

import com.hpmath.domain.directory.service.validation.DirectoryNameConstraint;
import com.hpmath.domain.directory.service.validation.DirectoryPathConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateDirectoryDto(
        @DirectoryPathConstraint
        String targetDirPath,
        @DirectoryNameConstraint
        String newDirName,
        @NotNull
        Long requestMemberId
) {
}
