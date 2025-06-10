package com.hpmath.app.consumer.notification;

import com.hpmath.app.consumer.notification.handler.EventHandler;
import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventPayload;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationEventProcessor {
    private final List<EventHandler> eventHandlers;

    public void process(Event<? extends EventPayload> event) {
        eventHandlers.stream()
                .filter(handler -> handler.supports(event))
                .findAny()
                .ifPresentOrElse(
                        handler -> handler.handle(event),
                        () -> log.error("cannot find exact handler for: {}", event)
                );

    }
}
