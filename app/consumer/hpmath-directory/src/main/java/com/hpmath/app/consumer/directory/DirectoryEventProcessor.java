package com.hpmath.app.consumer.directory;

import com.hpmath.app.consumer.directory.handler.EventHandler;
import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventPayload;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DirectoryEventProcessor {
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
