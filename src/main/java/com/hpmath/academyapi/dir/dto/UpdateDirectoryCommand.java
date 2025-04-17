package com.hpmath.academyapi.dir.dto;

import com.hpmath.academyapi.dir.dao.Directory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateDirectoryCommand(
        Directory directory,
        @Pattern(regexp = "^/+/&") String newDirName,
        @NotNull Long requestMemberId
) {
}
