package com.hpmath.domain.media.storage;

import com.hpmath.common.ErrorCode;
import com.hpmath.domain.media.exception.ChunkException;
import com.hpmath.domain.media.storage.uploadfile.ChunkUploadFileV2;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ChunkStorageV2 extends LocalStorage {
    // chunk_uniqueId_{uniqueId}_partNumber_{partNumber}
    private static final String FORMAT = "chunk_uniqueId_%s_partNumber_%s";
    private static final String FORMAT_REGEX = "chunk_uniqueId_.+_partNumber_.+";

    public ChunkStorageV2(@Value(value = "${server.local.chunk.storage}") String storagePath) {
        super(storagePath);
    }

    public void save(ChunkUploadFileV2 file) {
        final FormattedChunkUploadFile formattedChunkUploadFile = new FormattedChunkUploadFile(
                file.getUniqueId(),
                file.getInputStream(),
                file.getPartNumber(),
                FORMAT.formatted(file.getUniqueId(), file.getPartNumber()));
        super.store(formattedChunkUploadFile);
    }

    public List<String> loadAllUniqueIds() {
        return this.loadAllFileNames().stream()
                    .filter(fileName -> fileName.matches(FORMAT_REGEX))
                    .map(fileName -> fileName.split("_")[2])
                    .distinct()
                    .toList();
    }

    public void removeUniqueId(final String uniqueId) {
        loadFilesStartWith(FORMAT.formatted(uniqueId, ""))
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

    private Stream<Path> loadFilesStartWith(final String uniqueId) {
        try {
            return Files.walk(resolveFilePath(""))
                    .filter(Files::isRegularFile)
                    .filter(path -> String.valueOf(path.getFileName()).startsWith(uniqueId));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new ChunkException("청크파일 여는 중 예외 발생", ErrorCode.CHUNK_ACCESS_EXCEPTION);
        }
    }

    private List<InputStream> loadSequentialFiles(final String uniqueId) {
        return loadFilesStartWith(FORMAT.formatted(uniqueId, ""))
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
        final String sequenceValue = String.valueOf(path.getFileName()).split("_")[4];
        return Integer.parseInt(sequenceValue);
    }

    private static class FormattedChunkUploadFile extends ChunkUploadFileV2 {
        private final String formattedFileName;

        public FormattedChunkUploadFile(String uniqueId, InputStream inputStream, int partNumber, String formattedFileName) {
            super(uniqueId, inputStream, partNumber);
            this.formattedFileName = formattedFileName;
        }

        @Override
        public String getUniqueFileName() {
            return formattedFileName;
        }
    }
}
