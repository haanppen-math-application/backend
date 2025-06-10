package com.hpmath.app.api.notification;

import com.hpmath.domain.notification.NotificationQueryService;
import com.hpmath.domain.notification.dto.NotificationResult;
import com.hpmath.domain.notification.dto.QueryPagedNotificationCommand;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Service
@Validated
public class SseService {
    private final SseConfigurationProperties properties;
    private final SseLockManager sseLockManager;
    private final NotificationQueryService notificationQueryService;

    private final Duration initialLockTTL;
    private final Duration periodLockTTL;

    private static final ConcurrentHashMap<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

    public SseService(
            NotificationQueryService notificationQueryService,
            SseLockManager sseLockManager,
            SseConfigurationProperties properties
    ) {
        this.notificationQueryService = notificationQueryService;
        this.sseLockManager = sseLockManager;
        this.properties = properties;

        initialLockTTL = Duration.ofMinutes(properties.getInitialDelaySeconds() + 1);
        periodLockTTL = Duration.ofMinutes(properties.getPeriodSeconds() + 1);
    }

    public SseEmitter register(@NotNull final Long memberId) {
        if (sseLockManager.tryLock(memberId, initialLockTTL)) {
            final SseEmitter emitter = getEmitter(memberId);
            registerNotificationListQuerySchedule(emitter, memberId);
            registerNotReadCountQuerySchedule(emitter, memberId);
            emitters.put(memberId, emitter);
            return emitter;
        }
        throw new IllegalStateException("connection already established");
    }

    private void registerNotificationListQuerySchedule(final SseEmitter emitter, final Long memberId) {
        log.debug("WORK REGISTERED IN SCHEDULER");
        scheduler.scheduleAtFixedRate(() -> {
            final List<NotificationResult> results = getNotificationResults(memberId);
            try {
                sseLockManager.overrideLock(memberId, periodLockTTL);
                emitter.send(SseEmitter.event()
                        .name("notification")
                        .data(results, MediaType.APPLICATION_JSON)
                        .reconnectTime(1000L)
                        .build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, properties.getInitialDelaySeconds(), properties.getPeriodSeconds(), TimeUnit.SECONDS);
    }

    private void registerNotReadCountQuerySchedule(final SseEmitter emitter, final Long memberId) {
        log.debug("WORK REGISTERED IN SCHEDULER");
        scheduler.scheduleAtFixedRate(() -> {
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
        }, properties.getInitialDelaySeconds(), properties.getPeriodSeconds(), TimeUnit.SECONDS);
    }

    private SseEmitter getEmitter(final Long memberId) {
        final SseEmitter emitter = new SseEmitter(periodLockTTL.toMillis());
        emitter.onTimeout(() -> {
            log.info("SseEmitter timed out: {}", memberId);
            removeEmitter(memberId);
        });
        emitter.onError(throwable -> {
            log.error("SseEmitter error: {}", memberId, throwable);
            removeEmitter(memberId);
        });
        emitter.onCompletion(() -> {
            removeEmitter(memberId);
        });
        return emitter;
    }

    private void removeEmitter(final Long memberId) {
        emitters.remove(memberId);
        sseLockManager.unlock(memberId);
    }

    private List<NotificationResult> getNotificationResults(final Long memberId) {
        return notificationQueryService.queryWithPaged(
                new QueryPagedNotificationCommand(memberId, LocalDateTime.now(), properties.pageSize));
    }

    @Configuration
    @ConfigurationProperties("sse")
    @Getter
    @Setter
    @NoArgsConstructor
    private static class SseConfigurationProperties {
        private int pageSize = 30;
        private int initialDelaySeconds = 1;
        private int periodSeconds = 10;
    }
}
