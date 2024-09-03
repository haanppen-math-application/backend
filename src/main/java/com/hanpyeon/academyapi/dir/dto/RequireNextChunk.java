package com.hanpyeon.academyapi.dir.dto;

import lombok.Getter;

@Getter
public class RequireNextChunk {
    private final Long nextChunkIndex;
    private final Long remainSize;
    private final Boolean needMore;
    private final String information;

    private RequireNextChunk(final Long nextChunkIndex, final Long remainSize, final Boolean needMore, final String information) {
        this.nextChunkIndex = nextChunkIndex;
        this.remainSize = remainSize;
        this.needMore = needMore;
        this.information = information;
    }

    public static RequireNextChunk need(final Long requiringChunkIndex, final Long remainSize, final String information) {
        return new RequireNextChunk(requiringChunkIndex, remainSize, true, information);
    }
    public static RequireNextChunk need(final Long requiringChunkIndex, final Long remainSize) {
        return new RequireNextChunk(requiringChunkIndex, remainSize, true, null);
    }

    public static RequireNextChunk completed() {
        return new RequireNextChunk(null, 0L, false, null);
    }
}
