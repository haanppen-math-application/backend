package com.hpmath.domain.course.dto;

import com.hpmath.common.Role;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record RegisterStudentCommand(
        @NotNull Long courseId,
        @NotNull Long requestMemberId,
        @NotNull Role role,
        @NotNull List<Long> studentIds)
{
}
