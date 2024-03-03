package com.hanpyeon.academyapi.board.dto;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record QuestionUpdateDto (
        String content,
        Long targetMemberId,
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
