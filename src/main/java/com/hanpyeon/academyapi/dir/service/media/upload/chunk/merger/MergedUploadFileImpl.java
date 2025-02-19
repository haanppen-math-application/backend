package com.hanpyeon.academyapi.dir.service.media.upload.chunk.merger;

import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkGroupInfo;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;

@RequiredArgsConstructor
class MergedUploadFileImpl implements MergedUploadFile {

    private final ChunkGroupInfo chunkGroupInfo;
    private final File mergedFile;
    private final ChunkStorage temporarySavedStorage;
    private final Long duration;

    @Override
    public String getUniqueFileName() {
        return "result_" + chunkGroupInfo.getGroupId() + chunkGroupInfo.getExtension();
    }

    @Override
    public InputStream getInputStream() {
        try {
            return new FileInputStream(mergedFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getExtension() {
        return chunkGroupInfo.getExtension();
    }

    @Override
    public boolean completed() {
        temporarySavedStorage.removeChunks(this.chunkGroupInfo);
        mergedFile.delete();
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
        return mergedFile.length();
    }
}
