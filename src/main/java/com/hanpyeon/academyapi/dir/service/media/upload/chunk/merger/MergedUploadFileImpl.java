package com.hanpyeon.academyapi.dir.service.media.upload.chunk.merger;

import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkGroupInfo;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorage;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;

@RequiredArgsConstructor
class MergedUploadFileImpl implements MergedUploadFile {

    private final ChunkGroupInfo chunkGroupInfo;
    private final InputStream inputStream;
    private final ChunkStorage temporarySavedStorage;

    @Override
    public String getUniqueFileName() {
        return "result_" + chunkGroupInfo.getGroupId() + chunkGroupInfo.getExtension();
    }

    @Override
    public InputStream getInputStream() {
        return this.inputStream;
    }

    @Override
    public String getExtension() {
        return chunkGroupInfo.getExtension();
    }

    @Override
    public boolean completed() {
        temporarySavedStorage.removeChunks(this.chunkGroupInfo);
        return this.chunkGroupInfo.clear();
    }
}
