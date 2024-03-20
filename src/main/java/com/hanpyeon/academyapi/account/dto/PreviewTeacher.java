package com.hanpyeon.academyapi.account.dto;

import com.hanpyeon.academyapi.account.entity.Member;

import java.time.LocalDateTime;

public record PreviewTeacher(
        Long id,
        String name,
        String phoneNumber,
        LocalDateTime registeredDateTime
) {
    public static PreviewTeacher of(final Member member) {
        return new PreviewTeacher(
                member.getId(),
                member.getName(),
                member.getPhoneNumber(),
                member.getRegisteredDate()
        );
    }
}
