package com.hpmath.hpmathcoreapi.online.dto;


import com.hpmath.hpmathcore.Role;

public record OnlineLessonQueryCommand(
        Long courseId,
        Long requestMemberId,
        Role requestMemberRole
) {
}
