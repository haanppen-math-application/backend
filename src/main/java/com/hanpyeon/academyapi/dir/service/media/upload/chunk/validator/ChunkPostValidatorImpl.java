package com.hanpyeon.academyapi.dir.service.media.upload.chunk.validator;

import com.hanpyeon.academyapi.dir.dao.DirectoryRepository;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ChunkPostValidatorImpl implements ChunkPostValidator {
    private final DirectoryRepository directoryRepository;

    @Override
    public void postValidate(ChunkedFile chunkedFile, ChunkStorage chunkStorage) {
    }

    private void isOverSize(ChunkedFile chunkedFile) {
    }
}
