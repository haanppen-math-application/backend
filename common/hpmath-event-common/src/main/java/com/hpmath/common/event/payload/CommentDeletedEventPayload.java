package com.hpmath.common.event.payload;

import com.hpmath.common.event.EventPayload;
import java.util.List;

public record CommentDeletedEventPayload(
        Long questionId,
        Long questionCommentCount,
        Long commentId,
        Long registeredMemberId,
        List<String> deletedMediaSrcs
) implements EventPayload {
}
