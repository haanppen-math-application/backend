package com.hanpyeon.academyapi.media.service.upload.chunk.merger;

import com.hanpyeon.academyapi.media.service.UploadFile;

public interface MergedUploadFile extends UploadFile {
    boolean completed();
    Long getDuration();
    Long getSize();
}
