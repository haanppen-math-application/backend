package com.hanpyeon.academyapi.dir.service.media.upload.chunk.validator;

import com.hanpyeon.academyapi.dir.service.media.upload.ChunkGroup;
import com.hanpyeon.academyapi.dir.service.media.upload.ChunkStorage;
import com.hanpyeon.academyapi.dir.service.media.upload.ChunkedFile;
import org.springframework.stereotype.Service;

@Service
class ChunkPostValidatorImpl implements ChunkPostValidator {
    @Override
    public void postValidate(ChunkedFile chunkedFile, ChunkStorage chunkStorage) {
    }

    private void isOverSize(ChunkedFile chunkedFile) {
    }
}
