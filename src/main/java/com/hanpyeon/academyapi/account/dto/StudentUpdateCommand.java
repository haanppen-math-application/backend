package com.hanpyeon.academyapi.account.dto;

public record StudentUpdateCommand(
        Long memberId,
        String phoneNumber,
        String name,
        Integer grade
) {
}
