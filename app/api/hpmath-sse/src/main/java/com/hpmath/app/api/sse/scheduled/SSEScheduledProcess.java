package com.hpmath.app.api.sse.scheduled;

import java.util.concurrent.ScheduledExecutorService;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@FunctionalInterface
public interface SSEScheduledProcess {
    void accept(final SseEmitter sseEmitter, final Long memberId, final ScheduledExecutorService service);
}
