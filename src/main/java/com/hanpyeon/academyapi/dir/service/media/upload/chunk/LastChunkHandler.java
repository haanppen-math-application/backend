package com.hanpyeon.academyapi.dir.service.media.upload.chunk;

import com.hanpyeon.academyapi.dir.dto.ChunkStoreResult;
import com.hanpyeon.academyapi.dir.service.media.upload.ChunkedFileTransferManager;
import com.hanpyeon.academyapi.dir.service.media.upload.DirectoryMediaUpdateManager;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
class LastChunkHandler implements ChunkHandler {
    private final ChunkedFileTransferManager chunkedFileTransferManager;
    private final DirectoryMediaUpdateManager directoryMediaUpdateManager;

    @Override
    @Transactional
    public ChunkStoreResult handle(ChunkedFile chunkedFile, ChunkStorage chunkStorage) {
        log.debug("RUNNED");
        final String savedFileName = chunkedFileTransferManager.sendToMediaStorage(chunkStorage, chunkedFile.getChunkGroupInfo());
        directoryMediaUpdateManager.update(chunkedFile.getChunkGroupInfo(), savedFileName, chunkedFile.getRequestMemberId());
        final String userDefinedFileName = chunkedFile.getChunkGroupInfo().getFileName();
        return ChunkStoreResult.completed(savedFileName, userDefinedFileName);
    }

    @Override
    public boolean applicable(ChunkedFile chunkedFile) {
        if (chunkedFile.isLast()) {
            return true;
        }
        return false;
    }
}
