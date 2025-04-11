package com.hanpyeon.academyapi.account.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SendValidationCodeCommand {
    private final String targetPhoneNumber;
    private final String validationCode;
    private final Integer currTryCount;
    private final Integer maxTryCount;
    private final Integer validTime;
}
