package com.hpmath.hpmathmediadomain.media.storage.uploadfile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;
/**
 * 힙 메모리에 데이터를 로드하는 방식의 UploadFile 추상 클래스입니다.
 * 데이터를 전부 메모리에 적재함으로 메모리 사용량이 많다는 단점이 있습니다.
 * 따라서 데이터의 크기에 주의해야 합니다.
 *
 * <p>
 * MultiPartFile 대한 비동기처리를 안전하게 할 수 있도록 돕습니다.
 */
public class AsyncImageUploadFile extends ImageUploadFile implements AsyncUploadFile {
    private final byte[] data;

    public AsyncImageUploadFile(final MultipartFile multipartFile) {
        super(multipartFile);
        try {
            data = multipartFile.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(data);
    }
}
