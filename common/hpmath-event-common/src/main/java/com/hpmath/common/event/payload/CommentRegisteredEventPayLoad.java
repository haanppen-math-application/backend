package com.hpmath.common.event.payload;

import com.hpmath.common.event.EventPayload;
import java.util.List;

public record CommentRegisteredEventPayLoad(
        Long questionId,
        Long questionCommentCount,
        Long commentId,
        String content,
        Long registeredMemberId,
        List<String> imageSrcs
) implements EventPayload {
}
