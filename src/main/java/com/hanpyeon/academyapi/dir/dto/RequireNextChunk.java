package com.hanpyeon.academyapi.dir.dto;

import lombok.Getter;

@Getter
public class RequireNextChunk {
    private final Long nextChunkIndex;
    private final Long remainSize;
    private final Boolean needMore;

    private RequireNextChunk(final Long nextChunkIndex, final Long remainSize, final Boolean needMore) {
        this.nextChunkIndex = nextChunkIndex;
        this.remainSize = remainSize;
        this.needMore = needMore;
    }

    public static RequireNextChunk needMore(final Long requiringChunkIndex, final Long remainSize) {
        return new RequireNextChunk(requiringChunkIndex, remainSize, true);
    }

    public static RequireNextChunk completed() {
        return new RequireNextChunk(null, 0L, false);
    }
}
