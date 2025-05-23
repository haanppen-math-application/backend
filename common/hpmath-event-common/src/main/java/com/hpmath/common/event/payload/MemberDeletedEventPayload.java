package com.hpmath.common.event.payload;

import com.hpmath.common.Role;
import com.hpmath.common.event.EventPayload;
import java.util.List;

public record MemberDeletedEventPayload(
        Long memberId,
        String phoneNumber,
        String name,
        Role role,
        Integer grade
) implements EventPayload {
}
