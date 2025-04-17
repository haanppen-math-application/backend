package com.hpmath.hpmathcoreapi.account.dto;

public record VerifyAccountCodeCommand(
        String phoneNumber,
        String verificationCode
){
}
