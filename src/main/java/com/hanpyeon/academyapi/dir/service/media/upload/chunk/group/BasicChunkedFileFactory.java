package com.hanpyeon.academyapi.dir.service.media.upload.chunk.group;

import com.hanpyeon.academyapi.dir.dto.UploadMediaCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class BasicChunkedFileFactory {

    public ChunkedFile create(final UploadMediaCommand uploadMediaDto, final ChunkGroupInfo chunkGroupInfo) {
        return new BasicChunkedFile(
                uploadMediaDto.getFile(),
                chunkGroupInfo,
                uploadMediaDto.getRequestMemberId(),
                uploadMediaDto.getIsLast(),
                uploadMediaDto.getCurrChunkIndex()
        );
    }
}
