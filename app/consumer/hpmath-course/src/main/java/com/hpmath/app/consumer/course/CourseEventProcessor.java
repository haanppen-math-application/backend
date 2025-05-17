package com.hpmath.app.consumer.course;

import com.hpmath.app.consumer.course.handler.EventHandler;
import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventPayload;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseEventProcessor {
    private final List<EventHandler> eventHandlers;

    public void process(Event<? extends EventPayload> event) {
        eventHandlers.stream()
                .filter(eventHandler -> eventHandler.applicable(event))
                .findAny()
                .ifPresentOrElse(
                        eventHandler -> eventHandler.handle(event),
                        () -> log.error("No handler found for event {}", event)
                );
    }
}
