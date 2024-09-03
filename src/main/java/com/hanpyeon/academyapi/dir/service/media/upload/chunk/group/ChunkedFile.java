package com.hanpyeon.academyapi.dir.service.media.upload.chunk.group;

import com.hanpyeon.academyapi.media.service.UploadFile;

public interface ChunkedFile extends UploadFile {
    ChunkGroupInfo getChunkGroupInfo();
    boolean isLast();
    void validateChunkIndex();
    void validateCurrSize();
    void updateCurrentInfoChunkIndex();
}
