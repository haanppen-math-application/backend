package com.hanpyeon.academyapi.media.storage;

import com.hanpyeon.academyapi.media.storage.LocalStorage;
import jakarta.transaction.Transactional;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootTest
class LocalStorageTest {
    @Autowired
    LocalStorage localStorage;

    @Test
    @Transactional
    void testPath() throws IOException {
        File file = new File("src/test/java/com/hanpyeon/academyapi/board/service/storage/temp.png");
        FileInputStream imageInputStream;
        try {
            imageInputStream = new FileInputStream(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String newFileName = localStorage.store(new MockMultipartFile("hee2", "temp.png", String.valueOf(ContentType.IMAGE_PNG), imageInputStream.readAllBytes()), "hee1.png");
    }

}