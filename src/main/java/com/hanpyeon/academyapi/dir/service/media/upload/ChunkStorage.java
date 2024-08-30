package com.hanpyeon.academyapi.dir.service.media.upload;

public interface ChunkStorage {
    void save(final ChunkedFile chunkedFile);

    ChunkGroup loadRelatedChunkedFiles(final ChunkGroupInfo chunkGroupInfo);

    void removeChunks(final ChunkGroupInfo chunkGroupInfo);
}
