package com.hanpyeon.academyapi.dir.service.media.upload.chunk.validator;

import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import org.springframework.stereotype.Service;

@Service
class ChunkOverSizeValidator implements ChunkValidator {
    @Override
    public void validate(ChunkedFile chunkedFile) {
        chunkedFile.validateCurrSize();
    }
}
