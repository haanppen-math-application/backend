package com.hanpyeon.academyapi.media.service.upload.chunk.validator;

import com.hanpyeon.academyapi.media.storage.uploadfile.ChunkedFile;

interface ChunkValidator {
    void validate(final ChunkedFile chunkedFile);
}
