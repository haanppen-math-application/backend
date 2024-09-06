package com.hanpyeon.academyapi.dir.dto;

import lombok.Getter;

@Getter
public class ChunkStoreResult {
    private final Boolean isCompleted;
    private final Boolean isWrongChunk;
    private final Long nextChunkIndex;
    private final Long remainSize;
    private final Boolean needMore;
    private final String errorInformation;
    private final String storedFilePath;
    private final String userFileName;

    private ChunkStoreResult(final Boolean isCompleted, final boolean isWrongChunk, final Long nextChunkIndex, final Long remainSize, final Boolean needMore, final String errorInformation, final String storedFilePath, final String userFileName) {
        this.isCompleted = isCompleted;
        this.isWrongChunk = isWrongChunk;
        this.nextChunkIndex = nextChunkIndex;
        this.remainSize = remainSize;
        this.needMore = needMore;
        this.errorInformation = errorInformation;
        this.storedFilePath = storedFilePath;
        this.userFileName = userFileName;
    }

    public static ChunkStoreResult error(final Long requiringChunkIndex, final Long remainSize, final String information) {
        return new ChunkStoreResult(false, true, requiringChunkIndex, remainSize, true, information, null, null);
    }

    public static ChunkStoreResult need(final Long requiringChunkIndex, final Long remainSize) {
        return new ChunkStoreResult(false, false, requiringChunkIndex, remainSize, true, null, null, null);
    }

    public static ChunkStoreResult completed(final String savedPath, final String userFileName) {
        return new ChunkStoreResult(true, false, null, 0L, false, null, savedPath, userFileName);
    }
}
