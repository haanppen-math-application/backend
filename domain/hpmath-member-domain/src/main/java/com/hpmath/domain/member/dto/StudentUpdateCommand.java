package com.hpmath.domain.member.dto;

public record StudentUpdateCommand(
        Long memberId,
        String phoneNumber,
        String name,
        Integer grade
) {
}
