package com.hpmath.domain.member.dto;

public record ChangedPassword(
        String phoneNumber,
        Password changedPassword
) {
}
