package com.hanpyeon.academyapi.course.application.dto;

public record ModifyMemoTextCommand(
        String title,
        String content,
        Long requestMemberId
) {
}
