package com.hanpyeon.academyapi.media.service.upload;

import com.hanpyeon.academyapi.media.dto.ChunkStoreResult;
import com.hanpyeon.academyapi.media.dto.UploadMediaCommand;
import com.hanpyeon.academyapi.dir.exception.ChunkException;
import com.hanpyeon.academyapi.media.service.upload.chunk.ChunkHandlerManager;
import com.hanpyeon.academyapi.media.service.upload.chunk.group.ChunkFactory;
import com.hanpyeon.academyapi.media.service.upload.chunk.group.ChunkGroupInfo;
import com.hanpyeon.academyapi.media.service.upload.chunk.group.ChunkedFile;
import com.hanpyeon.academyapi.media.service.upload.chunk.storage.ChunkStorage;
import com.hanpyeon.academyapi.media.service.upload.chunk.validator.ChunkValidateManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaUploadService {

    private final ChunkFactory chunkFactory;
    private final @Qualifier(value = "chunkStorage") ChunkStorage chunkStorage;
    private final ChunkHandlerManager chunkHandlerManager;
    private final ChunkValidateManager chunkValidateManager;

    public ChunkStoreResult upload(final UploadMediaCommand uploadMediaDto) {
        final ChunkedFile chunkedFile = chunkFactory.create(uploadMediaDto);
        try {
            chunkValidateManager.validate(chunkedFile);
        } catch (ChunkException exception) {
            return createRequireInfo(chunkedFile.getChunkGroupInfo(), exception);
        }
        return chunkHandlerManager.process(chunkedFile, chunkStorage);
    }

    private ChunkStoreResult createRequireInfo(final ChunkGroupInfo chunkGroupInfo, final ChunkException chunkException) {;
        final Long remain = chunkGroupInfo.getRequiringChunkSize();
        final Long nextIndex = chunkGroupInfo.getNextChunkIndex();
        return ChunkStoreResult.error(nextIndex, remain, chunkException.getMessage());
    }
}
