package com.hanpyeon.academyapi.account.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ModifyTeacherRequest(
        @NotNull
        Long targetId,
        @NotNull
        String name,
        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        String phoneNumber
) {
}
