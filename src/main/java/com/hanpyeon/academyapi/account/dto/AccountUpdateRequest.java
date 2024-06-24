package com.hanpyeon.academyapi.account.dto;

import jakarta.validation.constraints.Pattern;

public record AccountUpdateRequest(
        @Pattern(regexp = "^[0-9]+$")
        String phoneNumber,
        String name,
        String prevPassword,
        String newPassword
) {
}

