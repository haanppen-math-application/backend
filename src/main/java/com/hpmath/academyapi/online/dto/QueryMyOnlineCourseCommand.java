package com.hpmath.academyapi.online.dto;

import com.hpmath.academyapi.security.Role;

public record QueryMyOnlineCourseCommand(
        Long requestMemberId,
        Role requestMemberRole
) {
}
