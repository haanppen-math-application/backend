package com.hanpyeon.academyapi.media.storage;

import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.dto.MediaDto;
import com.hanpyeon.academyapi.media.exception.MediaStoreException;
import com.hanpyeon.academyapi.media.exception.NoSuchMediaException;
import com.hanpyeon.academyapi.media.exception.NotSupportedMediaException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class LocalStorage implements MediaStorage {

    @Value("${server.local.storage}")
    private String storagePath;

    @Override
    public String store(final MultipartFile multipartFile, final String newFileName) {
        final Path path = resolveFilePath(newFileName);
        try {
            multipartFile.transferTo(path);
            return newFileName;
        } catch (IOException e) {
            throw new MediaStoreException(ErrorCode.MEDIA_STORE_EXCEPTION);
        }
    }

    @Override
    public void remove(String fileName) {
        final Path absoluteMediaPath = this.resolveFilePath(fileName);
        absoluteMediaPath.toFile().delete();
    }

    @Override
    public MediaDto loadFile(final String fileName) {
        final Path absoluteImagePath = this.resolveFilePath(fileName);
        Resource fileResource = new FileSystemResource(absoluteImagePath);
        if (!fileResource.exists()) {
            throw new NoSuchMediaException(ErrorCode.NO_SUCH_MEDIA);
        }
        try {
            return new MediaDto(fileResource.getInputStream(), MediaType.parseMediaType(Files.probeContentType(fileResource.getFile().toPath())));
        } catch (IOException | InvalidMediaTypeException | InvalidPathException | SecurityException e) {
            throw new NotSupportedMediaException("지원하지 않는 이미지 입니다.", ErrorCode.NOT_SUPPORTED_MEDIA);
        }
    }

    private Path resolveFilePath(final String fileName) {
        return Paths.get(storagePath)
                .toAbsolutePath()
                .normalize()
                .resolve(fileName);
    }
}
