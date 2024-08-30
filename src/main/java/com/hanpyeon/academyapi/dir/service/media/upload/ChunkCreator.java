package com.hanpyeon.academyapi.dir.service.media.upload;

import com.hanpyeon.academyapi.dir.dto.UploadMediaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ChunkCreator {
    private final ChunkGroupInfoFactory chunkGroupFactory;
    private final BasicChunkedFileFactory videoChunkedFileFactory;

    public ChunkedFile create(final UploadMediaDto uploadMediaDto) {
        final ChunkGroupInfo chunkGroupInfo = chunkGroupFactory.create(uploadMediaDto);
        return videoChunkedFileFactory.create(
                uploadMediaDto.getFile(),
                chunkGroupInfo,
                uploadMediaDto.getIsLast()
        );
    }
}
