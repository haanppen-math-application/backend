package com.hanpyeon.academyapi.dir.service.media.upload;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
class BasicChunkedFileFactory {

    public ChunkedFile create(final MultipartFile file, final ChunkGroupInfo chunkGroupInfo, final Boolean isLast) {
        return new BasicChunkedFile(file, chunkGroupInfo, isLast);
    }
}
