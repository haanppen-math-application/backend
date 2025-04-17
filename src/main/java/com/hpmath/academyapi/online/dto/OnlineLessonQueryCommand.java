package com.hpmath.academyapi.online.dto;

import com.hpmath.academyapi.security.Role;

public record OnlineLessonQueryCommand(
        Long courseId,
        Long requestMemberId,
        Role requestMemberRole
) {
}
