package com.hanpyeon.academyapi.media.controller;

import com.hanpyeon.academyapi.media.dto.ChunkFileUploadCommand;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

class Requests {
    record ChunkUploadRequest(
            @NotBlank String fileName,
            @Nonnull Long totalChunkCount,
            @Nonnull Long currChunkIndex,
            @Nonnull Boolean isLast,
            @Nonnull String extension,
            @NonNull Long mediaDuration
    ) {

        ChunkFileUploadCommand toCommand(final MultipartFile multipartFile, final Long requestMemberId) {
            return new ChunkFileUploadCommand(
                    multipartFile,
                    fileName(),
                    totalChunkCount(),
                    currChunkIndex(),
                    isLast(),
                    requestMemberId,
                    extension(),
                    mediaDuration()
            );
        }
    }
}
