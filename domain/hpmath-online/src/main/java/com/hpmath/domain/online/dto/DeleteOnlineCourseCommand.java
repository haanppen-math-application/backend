package com.hpmath.domain.online.dto;


import com.hpmath.common.Role;

public record DeleteOnlineCourseCommand(
        Long courseId,
        Long requestMemberId,
        Role requestMemberRole
) {

}
