package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.dto.AttachmentUploadChunkFile;
import com.hanpyeon.academyapi.course.application.dto.ChunkProcessedResult;
import com.hanpyeon.academyapi.course.application.port.out.ChunkFileHandlerPort;
import com.hanpyeon.academyapi.media.dto.ChunkStoreResult;
import com.hanpyeon.academyapi.media.dto.ChunkFileUploadCommand;
import com.hanpyeon.academyapi.media.service.ChunkFileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChunkFileStoreAdapter implements ChunkFileHandlerPort {

    @Value("${memo.attachment.directory.path}")
    private String attachmentFileDirectoryPath;
    private final ChunkFileUploadService uploadService;


    @Override
    public ChunkProcessedResult process(AttachmentUploadChunkFile chunkFile) {
        final ChunkFileUploadCommand uploadMediaDto = create(chunkFile);
        final ChunkStoreResult result = uploadService.upload(uploadMediaDto);
        return ChunkProcessedResult.of(result);
    }

    private ChunkFileUploadCommand create(final AttachmentUploadChunkFile chunkFile) {
        return new ChunkFileUploadCommand(
                chunkFile.getFile(),
                chunkFile.getFileName(),
                chunkFile.getTotalChunkCount(),
                chunkFile.getCurrChunkIndex(),
                chunkFile.getIsLast(),
                chunkFile.getRequestMemberId(),
                chunkFile.getExtension(),
                null
        );
    }
}
