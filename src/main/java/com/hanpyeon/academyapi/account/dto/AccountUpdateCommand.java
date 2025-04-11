package com.hanpyeon.academyapi.account.dto;

import com.hanpyeon.academyapi.account.service.Password;
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
