package com.hanpyeon.academyapi.board.mapper;

import com.hanpyeon.academyapi.board.service.UploadFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MediaMapper {
    public UploadFile createUploadFile(final MultipartFile multipartFile) {
        return new UploadFile(multipartFile);
    }
}
