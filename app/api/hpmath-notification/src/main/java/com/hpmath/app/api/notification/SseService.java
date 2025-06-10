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
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SseService {
    private final SseConfigurationProperties properties;
    private final SseLockManager sseLockManager;
    private final NotificationQueryService notificationQueryService;

    private static final ConcurrentHashMap<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
    private final Duration TTL = Duration.ofMinutes(properties.getLockSeconds());

    public SseEmitter register(@NotNull final Long memberId) {
        if (sseLockManager.lock(memberId, TTL)) {
            final SseEmitter emitter = getEmitter(memberId);
            registerSchedule(emitter, memberId);
            emitters.put(memberId, emitter);
            return emitter;
        }
        throw new IllegalStateException("connection already established");
    }

    public void unregister(@NotNull final Long memberId) {
        final SseEmitter emitter = emitters.get(memberId);
        if (emitter != null) {
            emitter.complete();
        }
    }

    private void registerSchedule(final SseEmitter emitter, final Long memberId) {
        log.debug("WORK REGISTED IN SCHEDULER");
        scheduler.scheduleAtFixedRate(() -> {
            final List<NotificationResult> results = getNotificationResults(memberId);
            try {
                emitter.send(SseEmitter.event()
                        .name("notification")
                        .data(results, MediaType.APPLICATION_JSON)
                        .reconnectTime(1000L)
                        .build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, properties.getInitialDelaySeconds(), properties.periodSeconds, TimeUnit.SECONDS);
    }

    private SseEmitter getEmitter(final Long memberId) {
        final SseEmitter emitter = new SseEmitter(TTL.toMillis());
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
                new QueryPagedNotificationCommand(memberId, LocalDateTime.now(), properties.getPageSize()));
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
        private long lockSeconds = 30;
    }
}
