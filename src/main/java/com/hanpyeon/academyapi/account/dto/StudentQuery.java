package com.hanpyeon.academyapi.account.dto;

public record StudentQuery(
        Long cursorIndex,
        Integer pageSize,
        String name,
        Integer startGrade,
        Integer endGrade
) {
}
