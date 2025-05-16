package com.hpmath.domain.member.dto;

public record StudentQuery(
        Long cursorIndex,
        Integer pageSize,
        String name,
        Integer startGrade,
        Integer endGrade
) {
}
