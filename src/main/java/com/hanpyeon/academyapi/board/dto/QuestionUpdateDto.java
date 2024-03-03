package com.hanpyeon.academyapi.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record QuestionUpdateDto (
        @NotNull
        Long questionId,
        @NotBlank
        String content,
        @NotNull
        Long targetMemberId,
        @NotNull
        Long requestMemberId,
        List<MultipartFile> images
){
    @Override
    public String toString() {
        return "QuestionUpdateDto{" +
                "content='" + content + '\'' +
                ", targetMemberId=" + targetMemberId +
                ", requestMemberId=" + requestMemberId +
                ", images=" + images +
                '}';
    }
}
