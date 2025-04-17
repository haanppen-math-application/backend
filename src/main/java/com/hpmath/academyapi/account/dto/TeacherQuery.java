package com.hpmath.academyapi.account.dto;

public record TeacherQuery(
        Long cursorIndex,
        Integer pageSize,
        String name
) {
}
