package com.hpmath.common.event.payload;

import com.hpmath.common.event.EventPayload;

public record CommentRegisteredEventPayLoad(
        Long questionId,
        Long questionCommentCount,
        Long commentId,
        String content,
        Long registeredMemberId
) implements EventPayload {
}
