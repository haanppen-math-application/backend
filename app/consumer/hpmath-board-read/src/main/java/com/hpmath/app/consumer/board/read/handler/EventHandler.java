package com.hpmath.app.consumer.board.read.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventPayload;

public interface EventHandler<T extends EventPayload> {
    boolean supports(final Event<T> event);
    void handle(final Event<T> event);
}
