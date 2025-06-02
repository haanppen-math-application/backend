package com.hpmath.domain.member.dto;

import jakarta.validation.constraints.NotNull;

public record VerifyAccountCodeCommand(
        @NotNull
        String phoneNumber,
        @NotNull
        String verificationCode
){
}
