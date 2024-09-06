package com.hanpyeon.academyapi.course.application.dto;

public record AttachmentChunkResult(
        Boolean isUploaded,
        Boolean isWrongChunk,
        String information,
        Long nextRequireChunkIndex,
        Long remainSize
) {
}
