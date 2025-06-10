package com.hpmath.app.api.notification;

import com.hpmath.domain.notification.NotificationQueryService;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.time.Duration;
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

    private final Duration periodLockTTL;
    private final ScheduledExecutorService scheduler;

    private static final ConcurrentHashMap<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseService(
            NotificationQueryService notificationQueryService,
            SseLockManager sseLockManager,
            SseConfigurationProperties properties
    ) {
        this.notificationQueryService = notificationQueryService;
        this.sseLockManager = sseLockManager;
        this.properties = properties;

        periodLockTTL = Duration.ofSeconds(properties.getPeriodSeconds() + 1);
        log.info("periodLockTTL = {}", periodLockTTL);
        scheduler = Executors.newScheduledThreadPool(properties.threadPoolSize);
        log.info("scheduler = {}", scheduler);
    }

    public SseEmitter register(@NotNull final Long memberId) {
        if (sseLockManager.tryLock(memberId, periodLockTTL)) {
            final SseEmitter emitter = create(memberId);
            registerNotReadCountQuerySchedule(emitter, memberId);
            emitters.put(memberId, emitter);
            return emitter;
        }
        throw new IllegalStateException("connection already established");
    }

    private void registerNotReadCountQuerySchedule(final SseEmitter emitter, final Long memberId) {
        log.debug("WORK REGISTERED IN SCHEDULER");
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
        }, 1L, properties.getPeriodSeconds(), TimeUnit.SECONDS);
    }

    private SseEmitter create(final Long memberId) {
        final SseEmitter emitter = new SseEmitter(properties.getConnectionSeconds() * 1000L);
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

    @Configuration
    @ConfigurationProperties("sse")
    @Getter
    @Setter
    @NoArgsConstructor
    static class SseConfigurationProperties {
        private int pageSize = 30;
        private int connectionSeconds = 600;
        private int periodSeconds = 10;
        private int threadPoolSize = 10;
    }
}
