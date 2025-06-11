package com.hpmath.app.api.sse.scheduled;

import com.hpmath.app.api.sse.SSELockManager;
import com.hpmath.domain.notification.NotificationQueryService;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotReadCountProcess implements SSEScheduledProcess {
    private final SSELockManager sseLockManager;
    private final NotificationQueryService notificationQueryService;

    private static final Duration period = Duration.ofSeconds(5);
    private static final Duration periodLockTTL = period.plusSeconds(1);

    @Override
    public void accept(SseEmitter emitter, Long memberId, ScheduledExecutorService scheduler) {
        log.debug("not read count process Registered");
        scheduler.scheduleWithFixedDelay(() -> {
            final Integer notReadCount = notificationQueryService.queryNotReadCount(memberId);
            try {
                sseLockManager.overrideLock(memberId, periodLockTTL);
                emitter.send(SseEmitter.event()
                        .name("notification-not-read-count")
                        .data(notReadCount, MediaType.APPLICATION_JSON)
                        .reconnectTime(1000L)
                        .build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, 1L, period.getSeconds(), TimeUnit.SECONDS);
    }
}
