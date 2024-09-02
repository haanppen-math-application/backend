package com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage;

import com.hanpyeon.academyapi.dir.service.media.upload.ChunkGroup;
import com.hanpyeon.academyapi.dir.service.media.upload.ChunkGroupInfo;
import com.hanpyeon.academyapi.dir.service.media.upload.ChunkedFile;

public interface ChunkStorage {
    void save(final ChunkedFile chunkedFile);

    ChunkGroup loadRelatedChunkedFiles(final ChunkGroupInfo chunkGroupInfo);

    void removeChunks(final ChunkGroupInfo chunkGroupInfo);
}
