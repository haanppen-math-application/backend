package com.hanpyeon.academyapi.dir.service.media.upload;

import com.hanpyeon.academyapi.dir.exception.ChunkException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.storage.LocalStorage;
import com.hanpyeon.academyapi.media.storage.MediaStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
class LocalChunkStorage extends LocalStorage implements ChunkStorage {

    public LocalChunkStorage(@Value(value = "${server.local.chunk.storage}") String storagePath) {
        super(storagePath);
    }

    @Override
    public void save(ChunkedFile chunkedFile) {
        super.store(chunkedFile);
    }

    @Override
    public String transferTo(MediaStorage mediaStorage, ChunkGroupInfo chunkGroupInfo, ChunkMerger chunkMerger) {
        final MergedUploadFile mergedUploadFile = chunkMerger.merge(this, chunkGroupInfo);
        final String filePath = mediaStorage.store(mergedUploadFile);
        mergedUploadFile.completed();
        return filePath;
    }

    @Override
    public ChunkGroup loadRelatedChunkedFiles(final ChunkGroupInfo chunkGroupInfo) {
        return new ChunkGroup(chunkGroupInfo, loadRelatedFiles(chunkGroupInfo));
    }

    private List<Path> loadRelatedFiles(final ChunkGroupInfo chunkGroupInfo) {
        try {
            Stream<Path> pathStream = Files.walk(Paths.get(this.storagePath));
            return pathStream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().startsWith(chunkGroupInfo.getGroupId()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new ChunkException("청크파일 여는 중 예외 발생", ErrorCode.CHUNK_ACCESS_EXCEPTION);
        }
    }

    @Override
    @Async
    public void removeChunks(ChunkGroupInfo chunkGroupInfo) {
        final List<Path> paths = loadRelatedFiles(chunkGroupInfo);
        try {
            for (final Path path : paths) {
                Files.deleteIfExists(path);
            }
        } catch (IOException e) {
            throw new ChunkException("청크파일 삭제 불가", ErrorCode.CHUNK_ACCESS_EXCEPTION);
        }
    }
}
