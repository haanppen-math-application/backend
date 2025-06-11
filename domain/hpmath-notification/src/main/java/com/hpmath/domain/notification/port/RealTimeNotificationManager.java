package com.hpmath.domain.notification.port;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 실시간 알림을 위한 서비스
 */
@EnableScheduling
@Component
@RequiredArgsConstructor
class RealTimeNotificationManager {
    private final List<RealTimeNotificationPort> notificationPorts;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void realTimeNotification(final NotificationRegisteredEvent event) {
        notificationPorts.forEach(notificationPort -> notificationPort.sendNotification(event));
    }
}
