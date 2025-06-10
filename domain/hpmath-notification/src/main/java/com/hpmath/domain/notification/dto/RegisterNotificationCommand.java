package com.hpmath.domain.notification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterNotificationCommand(
        @NotBlank
        String message,
        @NotNull
        Long targetMemberId
) {
}
