package com.hanpyeon.academyapi.dir.service.media.upload.chunk.validator;

import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;

public interface ChunkPreValidator {
    void preValidate(final ChunkedFile chunkedFile);
}
