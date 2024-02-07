package com.hanpyeon.academyapi.account.service;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TimeProvider {
    public LocalDateTime getCurrTime() {
        return LocalDateTime.now();
    }
}
