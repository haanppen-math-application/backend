package com.hpmath.app.api.media.controller;


import com.hpmath.domain.media.dto.ChunkStoreResult;
import com.hpmath.domain.media.dto.RequiredChunkInfo;
import com.hpmath.domain.media.dto.UploadInitializeResult;
import java.util.List;

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

    record ChunkUploadStartResponse(
            String uniqueId
    ) {
        public static ChunkUploadStartResponse of(final UploadInitializeResult result) {
            return new ChunkUploadStartResponse(result.uniqueId());
        }
    }

    record RequiredChunkPartsResponse(
            List<Integer> requiredParts
    ) {
        public static RequiredChunkPartsResponse of(final RequiredChunkInfo info) {
            return new RequiredChunkPartsResponse(info.chunkNumbers());
        }
    }
}
