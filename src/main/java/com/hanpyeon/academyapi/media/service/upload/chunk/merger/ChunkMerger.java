package com.hanpyeon.academyapi.media.service.upload.chunk.merger;

import com.hanpyeon.academyapi.media.service.upload.chunk.group.ChunkGroupInfo;
import com.hanpyeon.academyapi.media.storage.ChunkStorage;
import com.hanpyeon.academyapi.media.storage.uploadfile.MergedUploadFile;

public interface ChunkMerger {
    MergedUploadFile merge(ChunkStorage chunkStorage, ChunkGroupInfo chunkGroupInfo);
}
