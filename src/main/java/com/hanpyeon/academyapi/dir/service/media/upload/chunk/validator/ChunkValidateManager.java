package com.hanpyeon.academyapi.dir.service.media.upload.chunk.validator;

import com.hanpyeon.academyapi.dir.service.media.upload.ChunkStorage;
import com.hanpyeon.academyapi.dir.service.media.upload.ChunkedFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChunkValidateManager {
    private final ChunkPostValidator chunkPostValidator;
    private final ChunkPreValidator chunkPreValidator;

    public void postValidate(final ChunkedFile chunkedFile, final ChunkStorage chunkStorage) {
        this.chunkPostValidator.postValidate(chunkedFile, chunkStorage);
    }

    public void preValidate(final ChunkedFile chunkedFile){
        this.chunkPreValidator.preValidate(chunkedFile);
    }
}
