package com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage;

import com.hanpyeon.academyapi.dir.exception.ChunkException;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkGroup;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkGroupInfo;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.storage.LocalStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
class LocalChunkStorage extends LocalStorage implements ChunkStorage {

    public LocalChunkStorage(@Value(value = "${server.local.chunk.storage}") String storagePath) {
        super(storagePath);
    }

    @Override
    public void save(ChunkedFile chunkedFile) {
        super.store(chunkedFile);
    }

    @Override
    public ChunkGroup loadRelatedChunkedFiles(final ChunkGroupInfo chunkGroupInfo) {
        return new ChunkGroup(chunkGroupInfo, loadRelatedFiles(chunkGroupInfo));
    }

    private List<Path> loadRelatedFiles(final ChunkGroupInfo chunkGroupInfo) {
        try {
            final String groupId = chunkGroupInfo.getGroupId();
            Stream<Path> pathStream = Files.walk(Paths.get(this.storagePath));
            final List<Path> resultPaths = new ArrayList<>();
            final List<Path> paths = pathStream.collect(Collectors.toList());
            for (final Path path : paths) {
                if (!Files.isRegularFile(path)) {
                    continue;
                }
                final String fileName = String.valueOf(path.getFileName());
                if (fileName.startsWith(groupId)) {
                    resultPaths.add(path);
                }
            }
            return resultPaths;
        } catch (IOException e) {
            throw new ChunkException("청크파일 여는 중 예외 발생", ErrorCode.CHUNK_ACCESS_EXCEPTION);
        }
    }

    /**
     * @param chunkGroupInfo ChunkStorage의 delete 오버헤드를 클라이언트가 감내해야할 이유 X
     *                       1. Async를 통한 비동기 로직 처리로 스레드를 더 소요 But 응답시간 단축
     *                       2. 스케쥴러를 통한 chunkFile 배치 삭제
     */
    @Override
    public void removeChunks(ChunkGroupInfo chunkGroupInfo) {
        log.debug("템프 파일 지우기 시작");
        final List<Path> paths = loadRelatedFiles(chunkGroupInfo);
        try {
            for (final Path path : paths) {
                log.debug(path + "지워지기 성공!");
                Files.deleteIfExists(path);
            }
        } catch (IOException e) {
            throw new ChunkException("청크파일 삭제 불가", ErrorCode.CHUNK_ACCESS_EXCEPTION);
        }
        log.debug("템프 파일 지우기 종료");
    }
}
