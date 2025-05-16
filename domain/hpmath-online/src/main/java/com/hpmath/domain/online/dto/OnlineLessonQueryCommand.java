package com.hpmath.domain.online.dto;


import com.hpmath.hpmathcore.Role;

public record OnlineLessonQueryCommand(
        Long courseId,
        Long requestMemberId,
        Role requestMemberRole
) {
}
