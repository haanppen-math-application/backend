package com.hpmath.common.event;

import com.hpmath.common.serializer.DataSerializer;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Event<T extends EventPayload> {
    private Long eventId;
    private EventType type;
    private T payload;

    public static <T extends EventPayload> Event<T> of(Long eventId, EventType type, T payload) {
        Event<T> event = new Event<>();
        event.eventId = eventId;
        event.type = type;
        event.payload = payload;
        return event;
    }

    public String toJson() {
        return DataSerializer.serialize(this);
    }

    public static Event<EventPayload> fromJson(String json) {
        EventRaw eventRaw = DataSerializer.deserialize(json, EventRaw.class);
        if (eventRaw == null) {
            return null;
        }
        Event<EventPayload> event = new Event<>();
        event.eventId = eventRaw.getEventId();
        event.type = EventType.from(eventRaw.getType());
        event.payload = DataSerializer.deserialize(eventRaw.getPayload(), event.type.getPayloadClass());
        return event;
    }

    @Getter
    private static class EventRaw {
        private Long eventId;
        private String type;
        private Object payload;
    }
}
