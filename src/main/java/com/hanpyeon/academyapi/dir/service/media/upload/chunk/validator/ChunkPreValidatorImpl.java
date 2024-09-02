package com.hanpyeon.academyapi.dir.service.media.upload.chunk.validator;

import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ChunkPreValidatorImpl implements ChunkPreValidator {

    @Override
    public void preValidate(ChunkedFile chunkedFile) {
//        this.validateChunkStartIndex(chunkedFile);
    }

    private void validateChunkStartIndex(final ChunkedFile chunkedFile) {
        chunkedFile.validateChunkIndex();
    }
}
