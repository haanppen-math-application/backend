package com.hpmath.hpmathcoreapi.course.application.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record RegisterStudentCommand(
        @NotNull Long courseId,
        @NotNull Long requestMemberId,
        @NotNull List<Long> targetMembersId)
{
}
