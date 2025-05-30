package com.hpmath.domain.online.dto;

import com.hpmath.common.BusinessException;
import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record AddOnlineCourseCommand(
        @NotNull Long requestMemberId,
        @NotNull Role requestMemberRole,
        @NotBlank String onlineCourseName,
        List<Long> students,
        @NotNull Long teacherId
) {
    public AddOnlineCourseCommand {
        validate(teacherId, requestMemberRole, requestMemberId);
    }

    private void validate(final Long teacherId, final Role role, final Long requestMemberId) {
        if (!role.equals(Role.TEACHER)) {
            return;
        }
        if (!teacherId.equals(requestMemberId)) {
            throw new BusinessException("선생은 본인의 강의만 생성할 수 있습니다.", ErrorCode.ONLINE_COURSE_EXCEPTION);
        }
    }
}
