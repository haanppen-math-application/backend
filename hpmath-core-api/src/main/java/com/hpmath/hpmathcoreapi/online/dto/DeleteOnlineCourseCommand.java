package com.hpmath.hpmathcoreapi.online.dto;


import com.hpmath.hpmathcore.Role;

public record DeleteOnlineCourseCommand(
        Long courseId,
        Long requestMemberId,
        Role requestMemberRole
) {

}
