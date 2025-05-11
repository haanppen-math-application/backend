package com.hpmath.hpmathcoreapi.online.dto;


import com.hpmath.hpmathcore.Role;

public record QueryMyOnlineCourseCommand(
        Long requestMemberId,
        Role requestMemberRole
) {
}
