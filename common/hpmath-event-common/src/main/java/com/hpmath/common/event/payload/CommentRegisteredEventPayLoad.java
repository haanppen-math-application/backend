package com.hpmath.common.event.payload;

import com.hpmath.common.event.EventPayload;
import java.time.LocalDateTime;
import java.util.List;

public record CommentRegisteredEventPayLoad(
        Long questionId,
        Long questionCommentCount,
        Long commentId,
        String content,
        Long registeredMemberId,
        LocalDateTime registeredDateTime,
        List<String> imageSrcs
) implements EventPayload {
}
