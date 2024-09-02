package com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage;

import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChunkStorageUploader {

    public void upload(final ChunkedFile chunkedFile, final ChunkStorage chunkStorage) {
        chunkStorage.save(chunkedFile);
        chunkedFile.updateCurrentInfoChunkIndex();
    }
}
