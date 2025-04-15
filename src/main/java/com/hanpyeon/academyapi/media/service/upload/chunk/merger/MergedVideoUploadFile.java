package com.hanpyeon.academyapi.media.service.upload.chunk.merger;

import com.hanpyeon.academyapi.media.storage.uploadfile.MergedUploadFile;

public interface MergedVideoUploadFile extends MergedUploadFile {
    Long getDuration();
}
