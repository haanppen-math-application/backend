package com.hanpyeon.academyapi.dir.service.media.upload.chunk.group;

import com.hanpyeon.academyapi.dir.dto.UploadMediaCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChunkFactory {
    private final ChunkGroupInfoFactory chunkGroupFactory;
    private final BasicChunkedFileFactory basicChunkedFileFactory;

    public ChunkedFile create(final UploadMediaCommand uploadMediaDto) {
        final ChunkGroupInfo chunkGroupInfo = chunkGroupFactory.create(uploadMediaDto);
        return basicChunkedFileFactory.create(uploadMediaDto, chunkGroupInfo);
    }
}
