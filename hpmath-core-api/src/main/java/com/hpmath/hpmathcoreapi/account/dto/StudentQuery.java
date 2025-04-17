package com.hpmath.hpmathcoreapi.account.dto;

public record StudentQuery(
        Long cursorIndex,
        Integer pageSize,
        String name,
        Integer startGrade,
        Integer endGrade
) {
}
