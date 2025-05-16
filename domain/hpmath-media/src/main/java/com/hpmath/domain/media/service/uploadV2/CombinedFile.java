package com.hpmath.domain.media.service.uploadV2;

import com.hpmath.domain.media.storage.uploadfile.UploadFile;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class CombinedFile implements UploadFile {
    private final InputStream inputStream;
    private final String uniqueId;
    private final String extension;

    @Override
    public String getUniqueFileName() {
        return uniqueId + extension;
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
