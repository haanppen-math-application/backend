package com.hanpyeon.academyapi.board.service;

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
    public void validateWith(UploadFileValidator uploadFileValidator) {
        if (!uploadFileValidator.validate(multipartFile)) {
            throw new IllegalArgumentException();
        }
    }
    public String uploadTo(MediaStorage storageService) {
        return storageService.store(multipartFile, newFileName);
    }
    private String getNewFileName(String fileName) {
        return UUID.randomUUID() + getExtension(fileName);
    }
    private String getExtension(String fileName) {
        final int extensionIdx = fileName.lastIndexOf(".");
        return fileName.substring(extensionIdx);
    }
}
