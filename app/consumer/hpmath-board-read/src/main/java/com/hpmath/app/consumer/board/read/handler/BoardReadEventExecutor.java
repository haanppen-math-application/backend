package com.hpmath.app.consumer.board.read.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventPayload;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardReadEventExecutor {
    private final List<EventHandler> handlers;

    public void handle(final Event<? extends EventPayload> event) {
        handlers.stream()
                .filter(boardReadEventHandler -> boardReadEventHandler.supports(event))
                .findAny()
                .ifPresentOrElse(
                        boardReadEventHandler -> boardReadEventHandler.handle(event),
                        () -> {});
    }
}
