package com.hanpyeon.academyapi.dir.service.media.upload;

import com.hanpyeon.academyapi.dir.dto.RequireNextChunk;
import com.hanpyeon.academyapi.dir.dto.UploadMediaDto;
import com.hanpyeon.academyapi.dir.exception.ChunkException;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.ChunkHandlerManager;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkFactory;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkGroupInfo;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorage;
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
    private final ChunkHandlerManager chunkHandlerManager;
    private final ChunkValidateManager chunkValidateManager;

    public RequireNextChunk upload(final UploadMediaDto uploadMediaDto) {
        final ChunkedFile chunkedFile = chunkFactory.create(uploadMediaDto);
        try {
            chunkValidateManager.validate(chunkedFile);
        } catch (ChunkException exception) {
            return createRequireInfo(chunkedFile.getChunkGroupInfo(), exception);
        }
        return chunkHandlerManager.process(chunkedFile, chunkStorage);
    }

    private RequireNextChunk createRequireInfo(final ChunkGroupInfo chunkGroupInfo, final ChunkException chunkException) {;
        final Long remain = chunkGroupInfo.getRequiringChunkSize();
        final Long nextIndex = chunkGroupInfo.getNextChunkIndex();
        return RequireNextChunk.need(nextIndex, remain, chunkException.getMessage());
    }
}
