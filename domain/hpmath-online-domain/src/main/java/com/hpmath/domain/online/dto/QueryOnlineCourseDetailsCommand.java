package com.hpmath.domain.online.dto;

import jakarta.validation.constraints.NotNull;

public record QueryOnlineCourseDetailsCommand(
        @NotNull Long onlineCourseId
) {
}
