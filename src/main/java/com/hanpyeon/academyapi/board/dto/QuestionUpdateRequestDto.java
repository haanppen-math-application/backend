package com.hanpyeon.academyapi.board.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record QuestionUpdateRequestDto(
        @NotNull
        Long questionId,
        String content,
        Long targetMemberId,
        List<MultipartFile> images
) {
    @Override
    public String toString() {
        return "QuestionUpdateRequestDto{" +
                "content='" + content + '\'' +
                ", targetMemberId=" + targetMemberId +
                ", images=" + images +
                '}';
    }
}
