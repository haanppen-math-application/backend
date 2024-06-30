package com.hanpyeon.academyapi.account.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AccountUpdateRequest(
        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        String phoneNumber,
        @NotNull
        String name,
        String prevPassword,
        String newPassword
) {
}

