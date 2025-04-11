package com.hanpyeon.academyapi.account.dto;

public record VerifyAccountCodeCommand(
        String phoneNumber,
        String verificationCode
){
}
