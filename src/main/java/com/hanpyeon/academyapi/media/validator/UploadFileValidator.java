package com.hanpyeon.academyapi.media.validator;

import com.hanpyeon.academyapi.media.storage.uploadfile.UploadFile;

public interface UploadFileValidator {
    boolean validate(UploadFile uploadFile);
}
