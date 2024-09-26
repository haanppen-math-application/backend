package com.hanpyeon.academyapi.account.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AccountUpdateCommand(
        @NotNull
        Long targetMemberId,
        @Pattern(regexp = "^[0-9]+$")
        String phoneNumber,
        String name,
        Integer grade,
        String prevPassword,
        String newPassword
) {
}
