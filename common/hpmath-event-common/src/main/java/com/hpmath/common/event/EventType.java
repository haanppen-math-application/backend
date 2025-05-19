package com.hpmath.common.event;

import com.hpmath.common.event.payload.CommentDeletedEventPayload;
import com.hpmath.common.event.payload.CommentRegisteredEventPayLoad;
import com.hpmath.common.event.payload.CommentUpdatedEventPayload;
import com.hpmath.common.event.payload.MemberDeletedEventPayload;
import com.hpmath.common.event.payload.NonePayload;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {
    NONE(NonePayload.class, Topic.HP_MATH_NONE),
    MEMBER_DELETED_EVENT(MemberDeletedEventPayload.class, Topic.HPMATH_MEMBER),
    COMMENT_REGISTERED_EVENT(CommentRegisteredEventPayLoad.class, Topic.HPMATH_BOARD),
    COMMENT_UPDATED_EVENT(CommentUpdatedEventPayload.class, Topic.HPMATH_BOARD),
    COMMENT_DELETED_EVENT(CommentDeletedEventPayload.class, Topic.HPMATH_BOARD);

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
        public static final String HPMATH_MEMBER = "hpmath-member";
        public static final String HPMATH_BOARD = "hpmath-board";
    }
}
