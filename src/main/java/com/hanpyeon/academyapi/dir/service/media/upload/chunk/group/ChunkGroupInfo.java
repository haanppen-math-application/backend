package com.hanpyeon.academyapi.dir.service.media.upload.chunk.group;

import com.hanpyeon.academyapi.dir.dto.RequireNextChunk;

public interface ChunkGroupInfo {

    void init();

    String getGroupId();

    String getChunkUniqueId();

    boolean clear();

    String getDirPath();

    String getFileName();
    String getExtension();

    boolean isAllReceived(final Long receivedFileSize);

    void isMatchToCurrIndex(final Long lastChunkIndex);

    void updateGroupIndex(final Long lastChunkSize);

    RequireNextChunk isCompleted();
}
