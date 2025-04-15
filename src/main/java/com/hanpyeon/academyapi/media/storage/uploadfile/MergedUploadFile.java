package com.hanpyeon.academyapi.media.storage.uploadfile;

public interface MergedUploadFile extends UploadFile {
    boolean completed();
    Long getDuration();
    Long getSize();
}
