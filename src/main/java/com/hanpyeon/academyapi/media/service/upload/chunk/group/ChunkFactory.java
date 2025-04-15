package com.hanpyeon.academyapi.media.service.upload.chunk.group;

import com.hanpyeon.academyapi.media.dto.UploadMediaCommand;
import com.hanpyeon.academyapi.media.storage.uploadfile.ChunkedFile;
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
