package com.hanpyeon.academyapi.media.validator;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFileValidator {
    boolean validate(MultipartFile file);
}
