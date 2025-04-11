package com.hanpyeon.academyapi.account.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SendPasswordCommand {
    private final String rawPassword;
}
