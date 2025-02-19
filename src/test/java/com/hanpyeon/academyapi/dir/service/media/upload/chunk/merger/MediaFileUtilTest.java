package com.hanpyeon.academyapi.dir.service.media.upload.chunk.merger;

import com.hanpyeon.academyapi.dir.service.media.upload.chunk.util.MediaFileUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MediaFileUtilTest {
    @Autowired
    private MediaFileUtil mediaFileUtil;

    @Test
    void 파일로부터_재생시간_추출() throws IOException {
        final Path path = Paths.get("/Users/yoon/projects/spring/math-backend/backend/src/main/resources/storage/dance.mp4");
        final File file = path.toFile();

        mediaFileUtil.getDurationFromPath(file);
        System.out.println(mediaFileUtil.getDurationFromPath(file) / 1000);
    }

}