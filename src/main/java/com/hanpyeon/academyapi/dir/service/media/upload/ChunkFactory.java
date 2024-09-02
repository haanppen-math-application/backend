package com.hanpyeon.academyapi.dir.service.media.upload;

import com.hanpyeon.academyapi.dir.dto.UploadMediaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ChunkFactory {
    private final ChunkGroupInfoFactory chunkGroupFactory;
    private final BasicChunkedFileFactory basicChunkedFileFactory;

    public ChunkedFile create(final UploadMediaDto uploadMediaDto) {
        final ChunkGroupInfo chunkGroupInfo = chunkGroupFactory.create(uploadMediaDto);
        return basicChunkedFileFactory.create(uploadMediaDto, chunkGroupInfo);
    }
}
