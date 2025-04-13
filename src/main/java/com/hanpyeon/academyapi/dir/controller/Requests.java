package com.hanpyeon.academyapi.dir.controller;

import com.hanpyeon.academyapi.dir.dto.ChunkStoreResult;
import com.hanpyeon.academyapi.dir.dto.CreateDirectoryCommand;
import com.hanpyeon.academyapi.dir.dto.DeleteDirectoryDto;
import com.hanpyeon.academyapi.dir.dto.UploadMediaCommand;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

public class Requests {
    record UpdateDirectoryRequest(
            @NotBlank @Pattern(regexp = "^/.*$") String targetDirPath,
            @NotBlank @Pattern(regexp = "^[가-힣a-zA-Z0-9 ]+$") String newDirName
    ) {
    }

    record CreateDirectoryRequest(
            @NotBlank @Pattern(regexp = "^/.*$") String directoryPath,
            @NotBlank @Pattern(regexp = "^[가-힣a-zA-Z0-9 ]+$") String directoryName,
            @NotNull Boolean canViewByEveryone,
            @NotNull Boolean canModifyByEveryone
    ) {
        CreateDirectoryCommand toCommand(final Long requestMemberId) {
            return new CreateDirectoryCommand(
                    directoryName(), directoryPath(), requestMemberId, canViewByEveryone, canModifyByEveryone
            );
        }
    }

    record DeleteDirectoryRequest(
            @NotBlank @Pattern(regexp = "^/.*$") String targetDirectory,
            @NotNull Boolean deleteChildes
    ) {
    }

    record MediaSaveResponse(
            Long nextChunkIndex,
            Long remainSize,
            Boolean needMore,
            Boolean isWrongChunk,
            String errorMessage
    ) {
        static MediaSaveResponse of(final ChunkStoreResult result) {
            return new MediaSaveResponse(
                    result.getNextChunkIndex(),
                    result.getRemainSize(),
                    result.getNeedMore(),
                    result.getIsWrongChunk(),
                    result.getErrorInformation()
            );
        }
    }

    record MediaSaveRequest(
            @NotBlank @Pattern(regexp = "^/.*$") String targetDirectoryPath,
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
                    targetDirectoryPath(),
                    extension(),
                    mediaDuration()
            );
        }
    }
}
