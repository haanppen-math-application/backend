package com.hpmath.app.consumer.board.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventPayload;

public interface EventHandler<T extends EventPayload> {
    void handle(Event<T> event);
    boolean canHandle(Event<T> event);
}
