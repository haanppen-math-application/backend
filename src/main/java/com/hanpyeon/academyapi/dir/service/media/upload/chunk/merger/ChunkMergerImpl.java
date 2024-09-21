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
        final InputStream totalInputStream = getCombinedInputStream(chunkGroup.getSequentialChunkInputStream());
        return new MergedUploadFileImpl(chunkGroup.getChunkGroupInfo(), totalInputStream, chunkStorage);
    }

    private ChunkGroup getValidatedChunkGroup(final ChunkStorage chunkStorage, final ChunkGroupInfo chunkGroupInfo) {
        final ChunkGroup chunkGroup = chunkStorage.loadSequentialRelatedChunkedFiles(chunkGroupInfo);
        try {
            chunkGroup.validateAllChunkFileReceived();
        } catch (ChunkException chunkException) {
            chunkStorage.removeChunks(chunkGroupInfo);
            chunkGroupInfo.clear();
            throw chunkException;
        }
        return chunkGroup;
    }

    private InputStream getCombinedInputStream(final List<InputStream> inputStreams) {
        return new SequenceInputStream(Collections.enumeration(inputStreams));
    }
}
