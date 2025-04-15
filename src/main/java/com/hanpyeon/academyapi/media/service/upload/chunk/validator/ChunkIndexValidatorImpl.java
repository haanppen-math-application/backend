package com.hanpyeon.academyapi.media.service.upload.chunk.validator;

import com.hanpyeon.academyapi.media.storage.uploadfile.ChunkedFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ChunkIndexValidatorImpl implements ChunkValidator {

    @Override
    public void validate(ChunkedFile chunkedFile) {
        chunkedFile.validateChunkIndex();
    }
}
