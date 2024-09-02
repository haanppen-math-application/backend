package com.hanpyeon.academyapi.dir.service.media.upload;

import com.hanpyeon.academyapi.dir.dto.RequireNextChunk;
import com.hanpyeon.academyapi.dir.dto.UploadMediaDto;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkFactory;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorage;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorageUploader;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.validator.ChunkValidateManager;
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
    private final ChunkValidateManager chunkValidateManager;
    private final ChunkStorageUploader chunkStorageUploader;

    public RequireNextChunk upload(final UploadMediaDto uploadMediaDto) {
        final ChunkedFile chunkedFile = getValidatedChunkedFile(uploadMediaDto);
        uploadToChunkStorage(chunkedFile);
        if (chunkedFile.isLast()) {
            final String savedFileName = chunkedFileTransferManager.sendToMediaStorage(chunkStorage, chunkedFile.getChunkGroupInfo());
            directoryMediaUpdateManager.update(chunkedFile.getChunkGroupInfo(), savedFileName);
            return RequireNextChunk.completed();
        }
        return RequireNextChunk.needMore(1L);
    }

    private ChunkedFile getValidatedChunkedFile(final UploadMediaDto uploadMediaDto) {
        final ChunkedFile chunkedFile = chunkFactory.create(uploadMediaDto);
        chunkValidateManager.preValidate(chunkedFile);
        return chunkedFile;
    }

    private void uploadToChunkStorage(final ChunkedFile chunkedFile) {
        chunkStorageUploader.upload(chunkedFile, chunkStorage);
        chunkValidateManager.postValidate(chunkedFile, chunkStorage);
    }
}
