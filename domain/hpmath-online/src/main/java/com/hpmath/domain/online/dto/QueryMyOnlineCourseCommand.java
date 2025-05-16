package com.hpmath.domain.online.dto;


import com.hpmath.common.Role;

public record QueryMyOnlineCourseCommand(
        Long requestMemberId,
        Role requestMemberRole
) {
}
