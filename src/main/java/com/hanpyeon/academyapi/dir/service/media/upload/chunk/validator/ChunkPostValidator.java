package com.hanpyeon.academyapi.dir.service.media.upload.chunk.validator;

import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorage;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;

public interface ChunkPostValidator {
    void postValidate(final ChunkedFile chunkedFile, final ChunkStorage chunkStorage);
}
