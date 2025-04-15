package com.hanpyeon.academyapi.media.storage.uploadfile;

import com.hanpyeon.academyapi.media.service.upload.chunk.group.ChunkGroupInfo;

public interface ChunkedFile extends UploadFile {
    ChunkGroupInfo getChunkGroupInfo();
    boolean isLast();
    Long getRequestMemberId();
    void validateChunkIndex();
    Long getRemainSizeWithThisChunk();
    void updateCurrentInfoChunkIndex();
}
