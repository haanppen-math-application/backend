package com.hpmath.app.api.directory.controller;

import com.hpmath.domain.directory.dto.CreateDirectoryCommand;
import com.hpmath.domain.directory.dto.SaveMediaToDirectoryCommand;
import com.hpmath.common.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

class Requests {
    record UpdateDirectoryRequest(
            @NotBlank @Pattern(regexp = "^/.*$") String targetDirPath,
            @NotBlank @Pattern(regexp = "^[가-힣a-zA-Z0-9 ]+$") String newDirName
    ) {
    }

    record CreateDirectoryRequest(
            @NotBlank @Pattern(regexp = "^/.*$") String directoryPath,
            @NotBlank @Pattern(regexp = "^[가-힣a-zA-Z0-9 ]+$") String directoryName,
            @NotNull Boolean canViewByEveryone,
            @NotNull Boolean canModifyByEveryone
    ) {
        CreateDirectoryCommand toCommand(final Long requestMemberId) {
            return new CreateDirectoryCommand(
                    directoryName(), directoryPath(), requestMemberId, canViewByEveryone, canModifyByEveryone
            );
        }
    }

    record DeleteDirectoryRequest(
            @NotBlank @Pattern(regexp = "^/.*$") String targetDirectory,
            @NotNull Boolean deleteChildes
    ) {
    }

    record SaveMediaToDirectoryRequest(
            String directoryPath,
            String mediaSrc
    ) {
        SaveMediaToDirectoryCommand toCommand(final Long requestMemberId, final Role role) {
            return new SaveMediaToDirectoryCommand(
                    directoryPath(),
                    mediaSrc(),
                    requestMemberId,
                    role
            );
        }
    }
}
