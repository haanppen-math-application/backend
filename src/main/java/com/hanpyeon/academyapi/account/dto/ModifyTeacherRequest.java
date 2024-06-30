package com.hanpyeon.academyapi.account.dto;

import jakarta.validation.constraints.NotNull;

public record ModifyTeacherRequest(
        @NotNull
        Long targetId,
        @NotNull
        String name,
        @NotNull
        String phoneNumber
) {
}
