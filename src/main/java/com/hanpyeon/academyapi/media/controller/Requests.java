package com.hanpyeon.academyapi.media.controller;

import com.hanpyeon.academyapi.media.dto.UploadMediaCommand;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

class Requests {
    record MediaSaveRequest(
            @NotBlank String fileName,
            @Nonnull Long totalChunkCount,
            @Nonnull Long currChunkIndex,
            @Nonnull Boolean isLast,
            @Nonnull String extension,
            @NonNull Long mediaDuration
    ) {

        UploadMediaCommand toCommand(final MultipartFile multipartFile, final Long requestMemberId) {
            return new UploadMediaCommand(
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
