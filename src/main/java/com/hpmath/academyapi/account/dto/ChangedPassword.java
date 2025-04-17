package com.hpmath.academyapi.account.dto;

public record ChangedPassword(
        String phoneNumber,
        Password changedPassword
) {
}
