package com.hpmath.domain.directory.dto;

import com.hpmath.domain.directory.dao.Directory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateDirectoryCommand(
        Directory directory,
        @Pattern(regexp = "^/+/&") String newDirName,
        @NotNull Long requestMemberId
) {
}
