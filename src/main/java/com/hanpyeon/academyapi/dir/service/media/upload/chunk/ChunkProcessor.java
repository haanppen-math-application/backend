package com.hanpyeon.academyapi.dir.service.media.upload.chunk;

import com.hanpyeon.academyapi.dir.dto.RequireNextChunk;
import com.hanpyeon.academyapi.dir.exception.ChunkException;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkGroupInfo;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorage;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorageUploader;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.validator.ChunkValidateManager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
abstract class ChunkProcessor {

    private final ChunkStorageUploader chunkStorageUploader;
    private final ChunkValidateManager chunkValidateManager;

    @Transactional
    RequireNextChunk process(final ChunkedFile chunkedFile, final ChunkStorage chunkStorage) {
        try {
            chunkValidateManager.validate(chunkedFile);
        } catch (ChunkException exception) {
            final ChunkGroupInfo chunkGroupInfo = chunkedFile.getChunkGroupInfo();
            final Long remain = chunkGroupInfo.getRequiringChunkSize();
            final Long nextIndex = chunkGroupInfo.getNextChunkIndex();
            return RequireNextChunk.need(nextIndex, remain, exception.getMessage());
        }
        chunkStorageUploader.upload(chunkedFile, chunkStorage);
        return handle(chunkedFile, chunkStorage);
    }

    protected abstract RequireNextChunk handle(final ChunkedFile chunkedFile, final ChunkStorage chunkStorage);

    protected abstract boolean applicable(final ChunkedFile chunkedFile);
}
