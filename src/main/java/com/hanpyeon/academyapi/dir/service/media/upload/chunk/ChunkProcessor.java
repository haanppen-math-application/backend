package com.hanpyeon.academyapi.dir.service.media.upload.chunk;

import com.hanpyeon.academyapi.dir.dto.RequireNextChunk;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorage;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorageUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
abstract class ChunkProcessor {

    private final ChunkStorageUploader chunkStorageUploader;

    @Transactional
    RequireNextChunk process(final ChunkedFile chunkedFile, final ChunkStorage chunkStorage) {
        chunkStorageUploader.upload(chunkedFile, chunkStorage);
        final RequireNextChunk requireNextChunk = handle(chunkedFile, chunkStorage);
        return requireNextChunk;
    }

    protected abstract RequireNextChunk handle(final ChunkedFile chunkedFile, final ChunkStorage chunkStorage);

    protected abstract boolean applicable(final ChunkedFile chunkedFile);
}
