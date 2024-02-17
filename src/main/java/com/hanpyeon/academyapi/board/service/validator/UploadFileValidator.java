package com.hanpyeon.academyapi.board.service.validator;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFileValidator {
    boolean validate(MultipartFile file);
}
