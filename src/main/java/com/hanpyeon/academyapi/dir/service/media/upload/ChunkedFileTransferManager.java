package com.hanpyeon.academyapi.dir.service.media.upload;

import com.hanpyeon.academyapi.dir.service.media.upload.chunk.merger.ChunkMerger;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.merger.MergedUploadFile;
import com.hanpyeon.academyapi.media.storage.MediaStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class ChunkedFileTransferManager {
    private final ChunkMerger chunkMerger;
    private final MediaStorage mediaStorage;

    public String sendToMediaStorage(final ChunkStorage chunkStorage, final ChunkGroupInfo chunkGroupInfo) {
        final MergedUploadFile mergedUploadFile = chunkMerger.merge(chunkStorage, chunkGroupInfo);
        final String resultFileName = mediaStorage.store(mergedUploadFile);
        mergedUploadFile.completed();
        return resultFileName;
    }
}
