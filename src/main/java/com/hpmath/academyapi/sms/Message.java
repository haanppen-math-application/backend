package com.hpmath.academyapi.sms;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Message {
    private final String targetPhoneNumber;
    private final String content;
}
