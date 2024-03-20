package com.hanpyeon.academyapi.account.dto;

import com.hanpyeon.academyapi.account.entity.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PreviewStudent(
        Long id,
        String name,
        String phoneNumber,
        Integer grade,
        LocalDateTime registeredDateTime
) {

    public static PreviewStudent of(final Member member) {
        return new PreviewStudent(
                member.getId(),
                member.getName(),
                member.getPhoneNumber(),
                member.getGrade(),
                member.getRegisteredDate()
        );
    }
}
