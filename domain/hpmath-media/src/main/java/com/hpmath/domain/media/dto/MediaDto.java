package com.hpmath.domain.media.dto;

import jakarta.validation.constraints.NotNull;
import java.io.InputStream;
import org.springframework.http.MediaType;

public record MediaDto(
        @NotNull
        InputStream data,
        @NotNull
        MediaType mediaType,
        @NotNull
        Long fileSize
) {
}
