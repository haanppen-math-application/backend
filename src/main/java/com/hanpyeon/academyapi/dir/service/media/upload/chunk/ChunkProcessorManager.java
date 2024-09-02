package com.hanpyeon.academyapi.dir.service.media.upload.chunk;

import com.hanpyeon.academyapi.dir.dto.RequireNextChunk;
import com.hanpyeon.academyapi.dir.exception.ChunkException;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorage;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChunkProcessorManager {

    private final List<ChunkProcessor> chunkProcessors;

    public RequireNextChunk process(final ChunkedFile chunkedFile, final ChunkStorage chunkStorage) {
        return chunkProcessors.stream()
                .filter(chunkPostHandler -> chunkPostHandler.applicable(chunkedFile))
                .findAny()
                .orElseThrow(() -> new ChunkException("해당 청크의 처리기를 찾을 수 없음", ErrorCode.CHUNK_HANDLE_EXCEPTION))
                .process(chunkedFile, chunkStorage);
    }
}
