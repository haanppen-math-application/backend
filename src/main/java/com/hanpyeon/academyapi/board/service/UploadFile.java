package com.hanpyeon.academyapi.board.service;

import com.hanpyeon.academyapi.board.exception.InvalidUploadFileException;
import com.hanpyeon.academyapi.board.service.storage.MediaStorage;
import com.hanpyeon.academyapi.board.service.validator.UploadFileValidator;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class UploadFile {
    private final MultipartFile multipartFile;
    private final String newFileName;

    public UploadFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
        newFileName = getNewFileName(multipartFile.getOriginalFilename());
    }
    public UploadFile validateWith(UploadFileValidator uploadFileValidator) {
        if (!uploadFileValidator.validate(multipartFile)) {
            throw new InvalidUploadFileException("업로드 할 수 없는 파일입니다.");
        }
        return this;
    }
    public String uploadTo(MediaStorage storageService) {
        return storageService.store(multipartFile, newFileName);
    }
    private String getNewFileName(String fileName) {
        return UUID.randomUUID() + getExtension(fileName);
    }
    private String getExtension(String fileName) {
        final int extensionIdx = fileName.lastIndexOf(".");
        if (extensionIdx == -1) {
            throw new InvalidUploadFileException("확장자를 찾을 수 없습니다.");
        }
        return fileName.substring(extensionIdx);
    }
}
