package com.hanpyeon.academyapi.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModifyStudentRequest(
        @NotNull
        Long studentId,
        @NotNull
        @NotBlank
        String name,
        @NotNull
        @NotBlank
        String phoneNumber,
        @NotNull
        Integer grade
) {
}
