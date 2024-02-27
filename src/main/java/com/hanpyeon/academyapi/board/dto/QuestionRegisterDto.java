package com.hanpyeon.academyapi.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record QuestionRegisterDto(
        @NotBlank
        String title,
        @NotBlank
        String content,
        @NotNull
        Long requestMemberId,
        @NotNull
        Long targetMemberId,
        List<MultipartFile> images
) {}
