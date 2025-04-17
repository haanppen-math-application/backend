package com.hpmath.hpmathcoreapi.account.dto;

public record TeacherQuery(
        Long cursorIndex,
        Integer pageSize,
        String name
) {
}
