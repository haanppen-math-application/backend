package com.hanpyeon.academyapi.account.dto;

import com.hanpyeon.academyapi.account.service.AccountPhoneNumber;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SendPasswordCommand {
    private final AccountPhoneNumber targetPhoneNumber;
    private final String rawPassword;
}
