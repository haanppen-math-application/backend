package com.hpmath.hpmathcoreapi.online.dto;

import com.hpmath.hpmathcoreapi.security.Role;

public record OnlineLessonQueryCommand(
        Long courseId,
        Long requestMemberId,
        Role requestMemberRole
) {
}
