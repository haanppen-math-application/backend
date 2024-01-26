package com.hanpyeon.academyapi.service;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TimeProvider {
    public LocalDateTime getCurrTime() {
        return LocalDateTime.now();
    }
}
