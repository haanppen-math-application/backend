package com.hanpyeon.academyapi.media.dto;

public record ChunkMergeCommandV2(
        String uniqueId,
        String userDefinedFileName,
        String extension,
        Long fileSize,
        Long duration,
        Long requestMemberId
) {
}
