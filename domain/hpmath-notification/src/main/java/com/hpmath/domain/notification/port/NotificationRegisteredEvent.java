package com.hpmath.domain.notification.port;

import com.hpmath.domain.notification.Notification;
import java.time.LocalDateTime;

public record NotificationRegisteredEvent(
        Long notificationId,
        String message,
        Long targetMemberId,
        LocalDateTime registeredAt
) {
    public static NotificationRegisteredEvent from(Notification notification) {
        return new NotificationRegisteredEvent(
                notification.getId(),
                notification.getMessage(),
                notification.getTargetMemberId(),
                notification.getRegisteredAt());
    }
}
