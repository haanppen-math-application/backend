package com.hpmath.app.consumer.media.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventPayload;

public interface EventHandler<T extends EventPayload> {
    boolean supports(Event<T> event);
    void handle(Event<T> event);
}
