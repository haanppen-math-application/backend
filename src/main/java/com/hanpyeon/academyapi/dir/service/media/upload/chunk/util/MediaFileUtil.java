package com.hanpyeon.academyapi.dir.service.media.upload.chunk.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.process.ProcessLocator;

@Slf4j
@Component
@RequiredArgsConstructor
public class MediaFileUtil {
    private final FFMpegProcessLocator FFMpegProcessLocator;

    public Long getDurationFromPath(final File file) {
        final MultimediaObject multimediaObject = new MultimediaObject(file, FFMpegProcessLocator);
        try {
            return multimediaObject.getInfo().getDuration() / 1000;
        } catch (EncoderException e) {
            log.error("영상 길이 추출중 에러 발생 : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * @param inputStream 파악하고자 하는 하는 데이터 스트림
     * @return 임시 파일
     * @throws IOException
     */
    public File createTempFile(final InputStream inputStream) throws IOException {
        final Path tempFilePath = Files.createTempFile(null, null,
                PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxrwx")));
        inputStream.transferTo(Files.newOutputStream(tempFilePath));
        return tempFilePath.toFile();
    }
}
