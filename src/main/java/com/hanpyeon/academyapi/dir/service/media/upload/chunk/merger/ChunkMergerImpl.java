package com.hanpyeon.academyapi.dir.service.media.upload.chunk.merger;

import com.hanpyeon.academyapi.dir.exception.ChunkException;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkGroup;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkGroupInfo;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorage;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
class ChunkMergerImpl implements ChunkMerger {

    @Override
    public MergedUploadFile merge(ChunkStorage chunkStorage, ChunkGroupInfo chunkGroupInfo) {
        final ChunkGroup chunkGroup = this.getValidatedChunkGroup(chunkStorage, chunkGroupInfo);
        final InputStream totalInputStream = getCombinedInputStream(getAllInputStreams(chunkGroup.getChunkPaths()));
        return new MergedUploadFileImpl(chunkGroup.getChunkGroupInfo(), totalInputStream, chunkStorage);
    }

    private ChunkGroup getValidatedChunkGroup(final ChunkStorage chunkStorage, final ChunkGroupInfo chunkGroupInfo) {
        final ChunkGroup chunkGroup = chunkStorage.loadRelatedChunkedFiles(chunkGroupInfo);
        try {
            chunkGroup.validateAllChunkFileReceived();
        } catch (ChunkException chunkException) {
            chunkStorage.removeChunks(chunkGroupInfo);
            chunkGroupInfo.clear();
            throw chunkException;
        }
        return chunkGroup;
    }

    private List<InputStream> getAllInputStreams(final List<Path> paths) {
        final List<InputStream> inputStreams = new ArrayList<>();
        try {
            for (final Path path : paths) {
                inputStreams.add(Files.newInputStream(path, StandardOpenOption.READ));
            }
            return Collections.unmodifiableList(inputStreams);
        } catch (IOException e) {
            throw new ChunkException(e + "청크 파일을 열 수 없음", ErrorCode.CHUNK_SIZE_EXCEPTION);
        }
    }

    private InputStream getCombinedInputStream(final List<InputStream> inputStreams) {
        return new SequenceInputStream(Collections.enumeration(inputStreams));
    }
}
