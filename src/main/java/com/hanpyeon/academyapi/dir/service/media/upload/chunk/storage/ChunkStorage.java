package com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage;

import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkGroup;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkGroupInfoImpl;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;

public interface ChunkStorage {
    void save(final ChunkedFile chunkedFile);

    ChunkGroup loadRelatedChunkedFiles(final ChunkGroupInfoImpl chunkGroupInfo);

    void removeChunks(final ChunkGroupInfoImpl chunkGroupInfo);
}
