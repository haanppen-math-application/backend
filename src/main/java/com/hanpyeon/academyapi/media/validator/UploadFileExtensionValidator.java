package com.hanpyeon.academyapi.media.validator;

import com.hanpyeon.academyapi.media.service.UploadFile;
import org.springframework.stereotype.Component;

@Component
public class UploadFileExtensionValidator implements UploadFileValidator {

    // 업로드 될 이미지에 대한 제약사항 구현
    @Override
    public boolean validate(UploadFile uploadFile) {
        return true;
    }
}
