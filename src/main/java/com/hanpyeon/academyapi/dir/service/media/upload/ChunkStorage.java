package com.hanpyeon.academyapi.dir.service.media.upload;

import com.hanpyeon.academyapi.media.storage.MediaStorage;

import java.util.List;

public interface ChunkStorage {
    void save(final ChunkedFile chunkedFile);
    String transferTo(final MediaStorage mediaStorage, final ChunkGroupInfo chunkGroupInfo, final ChunkMerger chunkMerger);
    ChunkGroup loadRelatedChunkedFiles(final ChunkGroupInfo chunkGroupInfo);
    void removeChunks(final ChunkGroupInfo chunkGroupInfo);
}
