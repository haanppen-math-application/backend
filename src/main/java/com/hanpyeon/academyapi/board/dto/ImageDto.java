package com.hanpyeon.academyapi.board.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

public record ImageDto(
        @NotNull
        Resource data,
        @NotNull
        MediaType mediaType
) {
}
