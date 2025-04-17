package com.hpmath.academyapi.media.service.uploadV2;

import com.hpmath.academyapi.media.storage.uploadfile.UploadFile;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CombinedFile implements UploadFile {
    private final InputStream inputStream;
    private final String uniqueId;
    private final String extension;

    @Override
    public String getUniqueFileName() {
        return uniqueId;
    }

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public String getExtension() {
        return extension;
    }
}
