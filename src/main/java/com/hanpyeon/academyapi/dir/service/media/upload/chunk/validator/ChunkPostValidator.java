package com.hanpyeon.academyapi.dir.service.media.upload.chunk.validator;

import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorage;
import com.hanpyeon.academyapi.dir.service.media.upload.ChunkedFile;

interface ChunkPostValidator {
    void postValidate(final ChunkedFile chunkedFile, final ChunkStorage chunkStorage);
}
