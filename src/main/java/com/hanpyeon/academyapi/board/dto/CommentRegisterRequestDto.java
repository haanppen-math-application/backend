package com.hanpyeon.academyapi.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Schema(description = "댓글 등록 DTO", requiredProperties = {"questionId"})
public record CommentRegisterRequestDto(
        @NotNull Long questionId,
        @NotNull String content,
        List<MultipartFile> images
) {
    @Override
    public String toString() {
        return "TestCommentRegisterDto{" +
                "questionId=" + questionId +
                ", content='" + content + '\'' +
                ", images=" + images +
                '}';
    }
}
