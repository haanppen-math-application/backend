package com.hpmath.domain.notification.dto;

import com.hpmath.domain.notification.Notification;
import jakarta.annotation.Nullable;
import java.time.LocalDateTime;

public record NotificationResult(
        Long notificationId,
        String message,
        Long targetMemberId,
        LocalDateTime registeredAt,
        @Nullable
        LocalDateTime readAt
) implements Comparable<NotificationResult> {

    public static NotificationResult from(final Notification notification) {
        return new NotificationResult(
                notification.getId(),
                notification.getMessage(),
                notification.getTargetMemberId(),
                notification.getRegisteredAt(),
                notification.getReadAt());
    }

    @Override
    public int compareTo(NotificationResult o) {
        if (this.readAt == null && o.readAt != null) {
            return -1;
        }
        return o.registeredAt.compareTo(registeredAt);
    }
}
