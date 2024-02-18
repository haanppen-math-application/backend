package com.hanpyeon.academyapi.board.service.validator;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UploadImageValidator implements UploadFileValidator {

    // 업로드 될 파일에 대한 제약사항 구현
    @Override
    public boolean validate(MultipartFile file) {
        if (file.isEmpty()) {
            return false;
        }
        return true;
    }
}
