package com.hpmath.domain.course.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record MemoRegisterCommand(
        @NotNull
        Long requestMemberId,
        @NotNull
        Long targetCourseId,
        @NotNull
        String title,
        @NotNull
        String content,
        @NotNull
        LocalDate registerTargetDate
) {
}
