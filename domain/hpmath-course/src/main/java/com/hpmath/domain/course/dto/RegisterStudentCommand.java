package com.hpmath.domain.course.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record RegisterStudentCommand(
        @NotNull Long courseId,
        @NotNull Long requestMemberId,
        @NotNull List<Long> targetMembersId)
{
}
