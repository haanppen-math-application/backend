package com.hanpyeon.academyapi.dir.dto;

import lombok.Getter;

@Getter
public class RequireNextChunk {
    private final Long requiringNextChunkIndex;
    private final Boolean needMore;

    private RequireNextChunk(final Long requiringNextChunkIndex, final Boolean needMore) {
        this.requiringNextChunkIndex = requiringNextChunkIndex;
        this.needMore = needMore;
    }

    public static RequireNextChunk needMore(final Long requiringChunkIndex) {
        return new RequireNextChunk(requiringChunkIndex, true);
    }

    public static RequireNextChunk completed() {
        return new RequireNextChunk(null, false);
    }
}
