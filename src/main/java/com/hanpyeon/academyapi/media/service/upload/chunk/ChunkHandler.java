package com.hanpyeon.academyapi.media.service.upload.chunk;

import com.hanpyeon.academyapi.media.dto.ChunkStoreResult;
import com.hanpyeon.academyapi.media.storage.uploadfile.ChunkedFile;
import com.hanpyeon.academyapi.media.storage.ChunkStorage;

interface ChunkHandler {

    ChunkStoreResult handle(final ChunkedFile chunkedFile, final ChunkStorage chunkStorage);

    boolean applicable(final ChunkedFile chunkedFile);
}
