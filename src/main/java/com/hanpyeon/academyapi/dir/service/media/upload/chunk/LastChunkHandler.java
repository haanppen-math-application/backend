package com.hanpyeon.academyapi.dir.service.media.upload.chunk;

import com.hanpyeon.academyapi.dir.dto.RequireNextChunk;
import com.hanpyeon.academyapi.dir.service.media.upload.ChunkedFileTransferManager;
import com.hanpyeon.academyapi.dir.service.media.upload.DirectoryMediaUpdateManager;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorage;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorageUploader;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.validator.ChunkValidateManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
class LastChunkHandler extends ChunkProcessor {
    private final ChunkedFileTransferManager chunkedFileTransferManager;
    private final DirectoryMediaUpdateManager directoryMediaUpdateManager;

    public LastChunkHandler(ChunkStorageUploader chunkStorageUploader, ChunkValidateManager chunkValidateManager, ChunkedFileTransferManager chunkedFileTransferManager, DirectoryMediaUpdateManager directoryMediaUpdateManager) {
        super(chunkStorageUploader, chunkValidateManager);
        this.chunkedFileTransferManager = chunkedFileTransferManager;
        this.directoryMediaUpdateManager = directoryMediaUpdateManager;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    protected RequireNextChunk handle(ChunkedFile chunkedFile, ChunkStorage chunkStorage) {
        final String savedFileName = chunkedFileTransferManager.sendToMediaStorage(chunkStorage, chunkedFile.getChunkGroupInfo());
        directoryMediaUpdateManager.update(chunkedFile.getChunkGroupInfo(), savedFileName);
        return RequireNextChunk.completed();
    }

    @Override
    public boolean applicable(ChunkedFile chunkedFile) {
        if (chunkedFile.isLast()) {
            return true;
        }
        return false;
    }
}
