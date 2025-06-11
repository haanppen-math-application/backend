package com.hpmath.domain.notification.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterNotificationCommand(
        @NotBlank
        String message,
        Long targetMemberId
) {
}
