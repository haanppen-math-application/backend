package com.hpmath.event;

import com.hpmath.event.payload.ImageDeleteEventPayload;
import com.hpmath.event.payload.NonePayload;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {
    NONE(NonePayload.class, Topic.HP_MATH_NONE),
    IMAGE_DELETED_EVENT(ImageDeleteEventPayload.class, Topic.HP_MATH_NONE);


    private final Class<? extends EventPayload> payloadClass;
    private final String topic;

    public static EventType from(String type) {
        try {
            return valueOf(type);
        } catch (Exception e) {
            log.error("[EventType.from] type={}", type, e);
            return null;
        }
    }

    public static class Topic {
        public static final String HP_MATH_NONE = "hpmath-none";
        public static final String HP_MATH_IMAGE_DELETE = "hpmath-none";
    }
}
