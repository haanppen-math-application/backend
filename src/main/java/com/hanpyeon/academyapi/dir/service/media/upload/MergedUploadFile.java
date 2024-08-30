package com.hanpyeon.academyapi.dir.service.media.upload;

import com.hanpyeon.academyapi.media.service.UploadFile;

interface MergedUploadFile extends UploadFile {
    boolean completed();
}
