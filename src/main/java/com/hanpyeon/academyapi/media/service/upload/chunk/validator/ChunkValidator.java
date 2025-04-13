package com.hanpyeon.academyapi.media.service.upload.chunk.validator;

import com.hanpyeon.academyapi.media.service.upload.chunk.group.ChunkedFile;

interface ChunkValidator {
    void validate(final ChunkedFile chunkedFile);
}
