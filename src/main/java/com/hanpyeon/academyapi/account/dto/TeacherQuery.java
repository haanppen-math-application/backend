package com.hanpyeon.academyapi.account.dto;

public record TeacherQuery(
        Long cursorIndex,
        Integer pageSize,
        String name
) {
}
