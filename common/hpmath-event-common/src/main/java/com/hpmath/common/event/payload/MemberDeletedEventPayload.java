package com.hpmath.common.event.payload;

import com.hpmath.common.event.EventPayload;
import java.util.List;

public record MemberDeletedEventPayload(
        List<Long> memberIds
) implements EventPayload {
}
