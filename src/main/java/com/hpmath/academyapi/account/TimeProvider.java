package com.hpmath.academyapi.account;

import java.time.LocalDateTime;

public class TimeProvider {
    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
