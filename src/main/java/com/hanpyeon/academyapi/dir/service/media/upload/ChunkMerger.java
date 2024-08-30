package com.hanpyeon.academyapi.dir.service.media.upload;

import java.nio.file.Path;

interface ChunkMerger {
    MergedUploadFile merge(ChunkStorage chunkStorage, ChunkGroupInfo chunkGroupInfo);
}
