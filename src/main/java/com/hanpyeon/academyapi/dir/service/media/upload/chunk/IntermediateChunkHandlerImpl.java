package com.hanpyeon.academyapi.dir.service.media.upload.chunk;

import com.hanpyeon.academyapi.dir.dto.RequireNextChunk;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkGroupInfo;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorage;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorageUploader;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.validator.ChunkPostValidator;
import org.springframework.stereotype.Service;

@Service
class IntermediateChunkHandlerImpl extends ChunkProcessor {


    public IntermediateChunkHandlerImpl(ChunkPostValidator chunkPostValidator, ChunkStorageUploader chunkStorageUploader) {
        super(chunkPostValidator, chunkStorageUploader);
    }

    @Override
    protected RequireNextChunk handle(ChunkedFile chunkedFile, ChunkStorage chunkStorage) {
        final ChunkGroupInfo chunkGroupInfo = chunkedFile.getChunkGroupInfo();
        final Long needSize = chunkGroupInfo.getRequiringChunkSize();
        final Long nextChunkIndex = chunkGroupInfo.getNextChunkIndex();
        return RequireNextChunk.needMore(nextChunkIndex, needSize);
    }

    @Override
    public boolean applicable(ChunkedFile chunkedFile) {
        if (chunkedFile.isLast()) {
            return false;
        }
        return true;
    }
}
