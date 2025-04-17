package com.hpmath.hpmathcoreapi.account.dto;

public record StudentUpdateCommand(
        Long memberId,
        String phoneNumber,
        String name,
        Integer grade
) {
}
