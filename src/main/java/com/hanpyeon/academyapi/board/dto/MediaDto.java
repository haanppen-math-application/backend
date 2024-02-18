package com.hanpyeon.academyapi.board.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;

import java.io.InputStream;

public record MediaDto(
        @NotNull
        InputStream data,
        @NotNull
        MediaType mediaType
) {
}
