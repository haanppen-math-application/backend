package com.hpmath.academyapi.account.dto;

import jakarta.validation.constraints.NotNull;

public record AccountUpdateCommand(
        @NotNull
        Long targetMemberId,
        String phoneNumber,
        String name,
        Password prevPassword,
        Password newPassword
) {
}
