package com.hpmath.domain.notification.port;

public interface RealTimeNotificationPort {
    void sendNotification(NotificationRegisteredEvent notificationRegisteredEvent);
}
