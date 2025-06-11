package com.hpmath.app.api.sse;

import com.hpmath.domain.notification.port.NotificationRegisteredEvent;
import com.hpmath.domain.notification.port.RealTimeNotificationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealTimeSSENotificationAdapter implements RealTimeNotificationPort {
    private final SSEService sseService;

    @Override
    public void sendNotification(NotificationRegisteredEvent event) {
        sseService.sendMessage(event.targetMemberId(), event.message(), "notification");
    }
}
