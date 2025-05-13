package com.hpmath.domain.online.dto;

public record OnlineStudentPreview(
        Long studentId,
        String studentName,
        Integer grade
) {
}
