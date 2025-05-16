package com.hpmath.hpmathmediadomain.media.service.uploadV2;

import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathmediadomain.media.exception.ChunkException;
import com.hpmath.hpmathmediadomain.media.storage.LocalStorage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ChunkStorageV2 extends LocalStorage {

    public ChunkStorageV2(@Value(value = "${server.local.chunk.storage}") String storagePath) {
        super(storagePath);
    }

    public void save(ChunkUploadFileV2 file) {
        super.store(file);
    }

    public void removeRelated(final String uniqueId) {
        loadPaths(uniqueId)
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public InputStream loadSequentialChunks(final String uniqueId) {
        return new BufferedInputStream(new SequenceInputStream(Collections.enumeration(loadSequentialFiles(uniqueId))));
    }

    private Stream<Path> loadPaths(final String uniqueId) {
        try {
            return Files.walk(Paths.get(this.storagePath))
                    .filter(Files::isRegularFile)
                    .filter(path -> String.valueOf(path.getFileName()).startsWith(uniqueId));
        } catch (IOException e) {
            throw new ChunkException("청크파일 여는 중 예외 발생", ErrorCode.CHUNK_ACCESS_EXCEPTION);
        }
    }

    private List<InputStream> loadSequentialFiles(final String uniqueId) {
        return loadPaths(uniqueId)
                .sorted((path1, path2) -> {
                    final int path1Sequence = getSequence(path1, uniqueId);
                    final int path2Sequence = getSequence(path2, uniqueId);
                    return path1Sequence - path2Sequence;
                })
                .map(path -> {
                    try {
                        return Files.newInputStream(path, StandardOpenOption.READ);
                    } catch (IOException e) {
                        throw new ChunkException("청크 접근중 에러 발생" + e.getMessage(), ErrorCode.CHUNK_ACCESS_EXCEPTION);
                    }
                })
                .collect(Collectors.toList());
    }

    private Integer getSequence(final Path path, final String uniqueId) {
        final String sequenceValue = String.valueOf(path.getFileName()).replace(uniqueId, "").replace("_", "");
        return Integer.parseInt(sequenceValue);
    }
}
