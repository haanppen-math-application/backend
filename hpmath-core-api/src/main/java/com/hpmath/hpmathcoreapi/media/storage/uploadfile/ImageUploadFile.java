package com.hpmath.hpmathcoreapi.media.storage.uploadfile;

import com.hpmath.hpmathcoreapi.exception.ErrorCode;
import com.hpmath.hpmathcoreapi.media.exception.InvalidUploadFileException;
import com.hpmath.hpmathcoreapi.media.exception.StorageException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

/**
 * UploadFile의 스트림 기반 추상 클래스 입니다. 메모리 사용량이 낮습니다.
 * <p>
 * 비동기 처리에 대해 안전하지 않습니다. ( MultipartFile )
 */
public class ImageUploadFile implements UploadFile {
    private final MultipartFile multipartFile;
    private final String newFileName;
    private final String extension;

    public ImageUploadFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
        this.newFileName = getNewFileName(multipartFile.getOriginalFilename());
        this.extension = parseExtension(multipartFile.getOriginalFilename());
    }

    private String getNewFileName(String fileName) {
        return UUID.randomUUID() + parseExtension(fileName);
    }

    private String parseExtension(String fileName) {
        final int extensionIdx = fileName.lastIndexOf(".");
        if (extensionIdx == -1) {
            throw new InvalidUploadFileException(ErrorCode.INVALID_UPLOAD_FILE);
        }
        return fileName.substring(extensionIdx);
    }

    @Override
    public String getUniqueFileName() {
        return this.newFileName;
    }

    @Override
    public InputStream getInputStream() {
        try {
            return multipartFile.getInputStream();
        } catch (IOException e) {
            throw new StorageException(ErrorCode.NO_SUCH_MEDIA);
        }
    }

    @Override
    public String getExtension() {
        return this.extension;
    }
}
