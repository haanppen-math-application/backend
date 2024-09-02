package com.hanpyeon.academyapi.dir.service.media.upload;

import com.hanpyeon.academyapi.dir.dto.RequireNextChunk;
import com.hanpyeon.academyapi.dir.dto.UploadMediaDto;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkFactory;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkGroupInfo;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadService {

    private final ChunkFactory chunkFactory;
    private final @Qualifier(value = "chunkStorage") ChunkStorage chunkStorage;
    private final ChunkedFileTransferManager chunkedFileTransferManager;
    private final DirectoryMediaUpdateManager directoryMediaUpdateManager;

    public RequireNextChunk upload(final UploadMediaDto uploadMediaDto) {
        final ChunkedFile chunkedFile = chunkFactory.create(uploadMediaDto);
        final RequireNextChunk requireNextChunk = this.uploadToChunkStorage(chunkedFile);

        if (requireNextChunk.getNeedMore()) {
            return requireNextChunk;
        }
        final String savedFileName = chunkedFileTransferManager.sendToMediaStorage(chunkStorage, chunkedFile.getChunkGroupInfo());
        directoryMediaUpdateManager.update(chunkedFile.getChunkGroupInfo(), savedFileName);
        return requireNextChunk;
    }

    private RequireNextChunk uploadToChunkStorage(final ChunkedFile chunkedFile) {
        chunkedFile.validateChunkIndex();
        chunkStorage.save(chunkedFile);
        final ChunkGroupInfo groupInfo = chunkedFile.getChunkGroupInfo();
        groupInfo.updateGroupIndex(chunkedFile.getChunkSize());
        return groupInfo.isCompleted();
    }
}
