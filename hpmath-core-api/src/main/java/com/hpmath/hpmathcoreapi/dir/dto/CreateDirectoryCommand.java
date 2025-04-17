package com.hpmath.hpmathcoreapi.dir.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.lang.NonNull;

public record CreateDirectoryCommand(
        @NotBlank @Pattern(regexp = "^[가-힣a-zA-Z0-9 ]+$") String directoryName,
        @NotBlank @Pattern(regexp = "^/+&") String parentDirPath,
        @NonNull Long ownerId,
        Boolean canViewByEveryone,
        Boolean canModifyByEveryone
) {
}
