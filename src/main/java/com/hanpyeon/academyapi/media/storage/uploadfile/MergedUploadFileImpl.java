package com.hanpyeon.academyapi.media.storage.uploadfile;

import com.hanpyeon.academyapi.media.service.upload.chunk.group.ChunkGroupInfo;
import com.hanpyeon.academyapi.media.storage.ChunkStorage;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MergedUploadFileImpl implements MergedUploadFile {

    private final ChunkGroupInfo chunkGroupInfo;
    private final InputStream mergedInputStream;
    private final ChunkStorage temporarySavedStorage;
    private final Long duration;

    @Override
    public String getUniqueFileName() {
        return "result_" + chunkGroupInfo.getGroupId() + chunkGroupInfo.getExtension();
    }

    @Override
    public InputStream getInputStream() {
        return mergedInputStream;
    }

    @Override
    public String getExtension() {
        return chunkGroupInfo.getExtension();
    }

    @Override
    public boolean completed() {
        log.debug("Completed chunk group {}", chunkGroupInfo.getGroupId());
        temporarySavedStorage.removeChunks(this.chunkGroupInfo);
        return this.chunkGroupInfo.clear();
    }

    /**
     * @return returns media file's running time
     */
    @Override
    public Long getDuration() {
        return duration;
    }

    @Override
    public Long getSize() {
        return chunkGroupInfo.getTotalSize();
    }
}
