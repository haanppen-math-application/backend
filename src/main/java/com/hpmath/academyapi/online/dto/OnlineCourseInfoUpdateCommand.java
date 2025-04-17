package com.hpmath.academyapi.online.dto;

import com.hpmath.academyapi.security.Role;

public record OnlineCourseInfoUpdateCommand(
        Long courseId,
        String courseName,
        Long teacherId,
        Long requestMemberId,
        Role requestMemberRole
) {
}
