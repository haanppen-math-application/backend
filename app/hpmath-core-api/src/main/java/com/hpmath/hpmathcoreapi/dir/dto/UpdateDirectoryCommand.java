package com.hpmath.hpmathcoreapi.dir.dto;

import com.hpmath.hpmathcoreapi.dir.dao.Directory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateDirectoryCommand(
        Directory directory,
        @Pattern(regexp = "^/+/&") String newDirName,
        @NotNull Long requestMemberId
) {
}
