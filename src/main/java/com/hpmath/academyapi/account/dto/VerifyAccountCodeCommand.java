package com.hpmath.academyapi.account.dto;

public record VerifyAccountCodeCommand(
        String phoneNumber,
        String verificationCode
){
}
