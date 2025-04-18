package com.hpmath.hpmathmediadomain.media.dto;

public record ChunkMergeCommandV2(
        String uniqueId,
        String userDefinedFileName,
        String extension,
        Long fileSize,
        Long duration,
        Long requestMemberId
) {
}
