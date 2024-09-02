package com.hanpyeon.academyapi.dir.service.media.upload.chunk.merger;

import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkGroupInfoImpl;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorage;

public interface ChunkMerger {
    MergedUploadFile merge(ChunkStorage chunkStorage, ChunkGroupInfoImpl chunkGroupInfo);
}
