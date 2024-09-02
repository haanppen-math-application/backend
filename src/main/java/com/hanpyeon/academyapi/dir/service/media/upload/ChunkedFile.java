package com.hanpyeon.academyapi.dir.service.media.upload;

import com.hanpyeon.academyapi.media.service.UploadFile;

public interface ChunkedFile extends UploadFile {
    ChunkGroupInfo getChunkGroupInfo();
    boolean isLast();
    void validateChunkIndex();
    Long getChunkSize();
}
