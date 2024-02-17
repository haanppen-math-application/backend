package com.hanpyeon.academyapi.board.service.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resource loadFile(final String fileName) {
        final Path absoluteImagePath = this.resolveFilePath(fileName);
        Resource systemResource = new FileSystemResource(absoluteImagePath);

        if (!systemResource.exists()) {
            throw new RuntimeException();
        }
        return systemResource;
    }

    private Path resolveFilePath(final String fileName) {
        return Paths.get(storagePath)
                .toAbsolutePath()
                .normalize()
                .resolve(fileName);
    }
}
