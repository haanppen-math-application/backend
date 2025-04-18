package com.hpmath.hpmathcoreapi.online.dto;


import com.hpmath.hpmathcore.Role;

public record OnlineCourseInfoUpdateCommand(
        Long courseId,
        String courseName,
        Long teacherId,
        Long requestMemberId,
        Role requestMemberRole
) {
}
