package com.hpmath.domain.member.dto;

public record VerifyAccountCodeCommand(
        String phoneNumber,
        String verificationCode
){
}
