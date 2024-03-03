package com.hanpyeon.academyapi.board.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record QuestionUpdateRequestDto(
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
