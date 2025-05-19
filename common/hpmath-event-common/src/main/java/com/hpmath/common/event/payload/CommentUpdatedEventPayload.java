package com.hpmath.common.event.payload;

import com.hpmath.common.event.EventPayload;
import java.util.List;

public record CommentUpdatedEventPayload(
        Long questionId,
        Long commentId,
        String content,
        Long registeredMemberId,
        List<String> deletedMediaSrcs
) implements EventPayload {
}
