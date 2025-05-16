package com.hpmath.domain.online.dto;


import com.hpmath.common.Role;

public record OnlineCourseInfoUpdateCommand(
        Long courseId,
        String courseName,
        Long teacherId,
        Long requestMemberId,
        Role requestMemberRole
) {
}
