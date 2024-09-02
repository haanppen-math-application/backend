package com.hanpyeon.academyapi.dir.service.media.upload.chunk.group;

import com.hanpyeon.academyapi.dir.dto.UploadMediaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class BasicChunkedFileFactory {

    public ChunkedFile create(final UploadMediaDto uploadMediaDto, final ChunkGroupInfoImpl chunkGroupInfo) {
        return new BasicChunkedFile(uploadMediaDto.getFile(),
                chunkGroupInfo,
                uploadMediaDto.getIsLast(),
                uploadMediaDto.getCurrChunkIndex()
        );
    }
}
