package com.hpmath.app.course.consumer.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventPayload;

public interface EventHandler<T extends EventPayload> {
    void handle(Event<T> event);
    boolean applicable(Event<T> event);
}
