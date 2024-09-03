package com.hanpyeon.academyapi.dir.service.media.upload.chunk.group;

import com.hanpyeon.academyapi.dir.exception.ChunkException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RequiredArgsConstructor
public class ChunkGroup {
    private final ChunkGroupInfo chunkGroupInfo;
    private final List<Path> chunkPaths;
    private final Long groupReceivedFileSize;

    public ChunkGroup(ChunkGroupInfo chunkGroupInfo, List<Path> chunkPaths) {
        this.chunkGroupInfo = chunkGroupInfo;
        this.chunkPaths = chunkPaths;
        this.groupReceivedFileSize = chunkPaths.stream()
                .mapToLong(path -> {
                    try {
                        return Files.size(path);
                    } catch (IOException e) {
                        throw new ChunkException("다운로드 된 청크들의 사이즈를 확인할 수 없습니다", ErrorCode.CHUNK_SIZE_EXCEPTION);
                    }
                }).sum();
    }

    public List<Path> getChunkPaths() {
        return chunkPaths;
    }

    public ChunkGroupInfo getChunkGroupInfo() {
        return chunkGroupInfo;
    }

    public void validateAllChunkFileReceived() {
        if (this.getChunkGroupInfo().chunkIndexFulfilled() && groupReceivedFileSize.equals(chunkGroupInfo.getNextChunkIndex() - 1L)) {
            throw new ChunkException("청크 다운로드 중 실패 발생", ErrorCode.CHUNK_GROUP_EXCEPTION);
        }
    }
}
