package com.hpmath.domain.media.storage.uploadfile;

import java.io.IOException;
import java.io.InputStream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@ToString
@RequiredArgsConstructor
@Getter
public class ChunkUploadFileV2 implements UploadFile {
    private final String uniqueId;
    private final InputStream inputStream;
    private final int partNumber;

    public ChunkUploadFileV2(String uniqueId, MultipartFile multipartFile, int partNumber) {
        this.uniqueId = uniqueId;
        try {
            this.inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.partNumber = partNumber;
    }

    @Override
    public String getUniqueFileName() {
        return uniqueId + "_" + partNumber;
    }

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public String getExtension() {
        return "";
    }
}
