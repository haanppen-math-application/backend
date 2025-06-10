package com.hpmath.domain.notification.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record QueryPagedNotificationCommand(
        @NotNull
        Long memberId,
        @NotNull
        LocalDateTime before,
        @NotNull
        @Max(100)
        Integer pageSize
) {
}
