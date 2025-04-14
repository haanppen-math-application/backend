package com.hanpyeon.academyapi.media.controller;


import com.hanpyeon.academyapi.media.dto.ChunkStoreResult;

public class Responses {
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
}
