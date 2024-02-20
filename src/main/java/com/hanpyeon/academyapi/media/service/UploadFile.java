package com.hanpyeon.academyapi.media.service;

import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.exception.InvalidUploadFileException;
import com.hanpyeon.academyapi.media.storage.MediaStorage;
import com.hanpyeon.academyapi.media.validator.UploadFileValidator;
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
            throw new InvalidUploadFileException(ErrorCode.INVALID_UPLOAD_FILE);
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
            throw new InvalidUploadFileException(ErrorCode.INVALID_UPLOAD_FILE);
        }
        return fileName.substring(extensionIdx);
    }
}
