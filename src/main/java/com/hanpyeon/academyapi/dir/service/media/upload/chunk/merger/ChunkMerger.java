package com.hanpyeon.academyapi.dir.service.media.upload.chunk.merger;

import com.hanpyeon.academyapi.dir.service.media.upload.ChunkGroupInfo;
import com.hanpyeon.academyapi.dir.service.media.upload.ChunkStorage;

public interface ChunkMerger {
    MergedUploadFile merge(ChunkStorage chunkStorage, ChunkGroupInfo chunkGroupInfo);
}
