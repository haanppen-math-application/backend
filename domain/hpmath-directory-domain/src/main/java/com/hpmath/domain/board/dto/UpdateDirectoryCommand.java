package com.hpmath.domain.board.dto;

import com.hpmath.domain.board.dao.Directory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateDirectoryCommand(
        Directory directory,
        @Pattern(regexp = "^/+/&") String newDirName,
        @NotNull Long requestMemberId
) {
}
