package com.hanpyeon.academyapi.online.dto;

import com.hanpyeon.academyapi.security.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record AddOnlineCourseCommand(
        @NotNull
        Long requestMemberId,
        @NotNull
        Role requestMemberRole,
        @NotBlank
        String onlineCourseName,
        List<Long> students,
        @NotNull
        Long teacherId
) {
}
