package com.hanpyeon.academyapi.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record CommentUpdateDto(
        @NotNull Long requestMemberId,
        @NotNull Long commentId,
        String content,
        List<MultipartFile> images
) {
}
