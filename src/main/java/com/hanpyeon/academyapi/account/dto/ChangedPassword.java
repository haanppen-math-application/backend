package com.hanpyeon.academyapi.account.dto;

import com.hanpyeon.academyapi.account.service.Password;

public record ChangedPassword(
        String phoneNumber,
        Password changedPassword
) {
}
