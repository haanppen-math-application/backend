package com.hanpyeon.academyapi.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Schema(description = "질문 작성 API")
public record QuestionRegisterRequestDto(
        String title,
        @NotBlank
        String content,
        @NotNull
        Long targetMemberId,
        List<MultipartFile> images
) {
    @Override
    public String toString() {
        return "TestQuestionRegisterRequestDto{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", targetMemberId=" + targetMemberId +
                ", images=" + images +
                '}';
    }
}
