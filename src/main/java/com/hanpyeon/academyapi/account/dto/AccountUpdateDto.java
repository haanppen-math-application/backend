package com.hanpyeon.academyapi.account.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AccountUpdateDto(

        @NotNull Long requestMemberId,

        @Pattern(regexp = "^[0-9]+$")

        String phoneNumber,

        String name
) {
}
