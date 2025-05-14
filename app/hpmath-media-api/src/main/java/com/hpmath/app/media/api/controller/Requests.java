package com.hpmath.app.media.api.controller;

import com.hpmath.hpmathmediadomain.media.dto.ChunkFileUploadCommand;
import com.hpmath.hpmathmediadomain.media.dto.ChunkMergeCommandV2;
import com.hpmath.hpmathmediadomain.media.dto.ChunkUploadCommand;
import com.hpmath.hpmathmediadomain.media.dto.ChunkUploadInitializeCommand;
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

    record ChunkUploadStartRequestV2(
            Integer totalPartCount
    ) {
        ChunkUploadInitializeCommand toCommand() {
            return new ChunkUploadInitializeCommand(totalPartCount);
        }
    }

    record ChunkMergeRequestV2(
            String uniqueId,
            String userDefinedFileName,
            String extension,
            Long fileSize,
            Long duration
    ) {
        ChunkMergeCommandV2 toCommand(final Long requestMemberId) {
            return new ChunkMergeCommandV2(
                    uniqueId(),
                    userDefinedFileName(),
                    extension(),
                    fileSize(),
                    duration(),
                    requestMemberId
            );
        }
    }

    record ChunkUploadRequestV2(
            MultipartFile file,
            Integer partNumber,
            String uniqueId
    ) {
        ChunkUploadCommand toCommand() {
            return new ChunkUploadCommand(uniqueId(), partNumber(), file);
        }
    }
}
