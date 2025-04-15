package com.hanpyeon.academyapi.media.service.upload.chunk.group;

import com.hanpyeon.academyapi.media.dto.ChunkFileUploadCommand;
import com.hanpyeon.academyapi.media.storage.uploadfile.BasicChunkedFile;
import com.hanpyeon.academyapi.media.storage.uploadfile.ChunkedFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class BasicChunkedFileFactory {

    public ChunkedFile create(final ChunkFileUploadCommand uploadMediaDto, final ChunkGroupInfo chunkGroupInfo) {
        return new BasicChunkedFile(
                uploadMediaDto.getFile(),
                chunkGroupInfo,
                uploadMediaDto.getRequestMemberId(),
                uploadMediaDto.getIsLast(),
                uploadMediaDto.getCurrChunkIndex()
        );
    }
}
