package com.hpmath.hpmathcoreapi.account.dto;

public record ChangedPassword(
        String phoneNumber,
        Password changedPassword
) {
}
