package com.hanpyeon.academyapi.dir.service.media.upload.chunk.group;

import com.hanpyeon.academyapi.dir.exception.ChunkException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@RequiredArgsConstructor
class BasicChunkedFile implements ChunkedFile {

    private final MultipartFile multipartFile;
    private final ChunkGroupInfo chunkGroupInfo;
    private final Boolean isLast;
    private final Long chunkStartIndex;
    private String uniqueChunkName = null;

    @Override
    public ChunkGroupInfo getChunkGroupInfo() {
        return chunkGroupInfo;
    }

    @Override
    public boolean isLast() {
        return isLast;
    }

    @Override
    public void validateChunkIndex() {
        chunkGroupInfo.isMatchToCurrIndex(chunkStartIndex);
    }

    @Override
    public Long getChunkSize() {
        return multipartFile.getSize();
    }

    @Override
    public String getUniqueFileName() {
        if (Objects.isNull(this.uniqueChunkName)) {
            this.uniqueChunkName = this.chunkGroupInfo.getChunkUniqueId();
        }
        return uniqueChunkName;
    }

    @Override
    public InputStream getInputStream() {
        try {
            return multipartFile.getInputStream();
        } catch (IOException e) {
            throw new ChunkException("청크 파일에 접근할 수 없음", ErrorCode.CHUNK_ACCESS_EXCEPTION);
        }
    }

    @Override
    public String getExtension() {
        return null;
    }
}
