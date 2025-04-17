package com.hpmath.academyapi.online.dto;

public record OnlineStudentPreview(
        Long studentId,
        String studentName,
        Integer grade
) {
}
