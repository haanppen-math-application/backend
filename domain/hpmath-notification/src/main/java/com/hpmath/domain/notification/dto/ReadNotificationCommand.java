package com.hpmath.domain.notification.dto;

import jakarta.validation.constraints.NotNull;

public record ReadNotificationCommand(
        @NotNull
        Long memberId,
        @NotNull
        Long notificationId
) {
}
