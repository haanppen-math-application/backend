package com.hpmath.domain.member.dto;

public record TeacherQuery(
        Long cursorIndex,
        Integer pageSize,
        String name
) {
}
