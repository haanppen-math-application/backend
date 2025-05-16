package com.hpmath.hpmathmediadomain.media.storage;

import com.hpmath.hpmathmediadomain.media.storage.uploadfile.ImageUploadFile;
import jakarta.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
@ActiveProfiles("test")
class LocalStorageTest {
    @Autowired
    LocalStorage localStorage;
    private static Path tempDirectory;

    @BeforeAll
    static void setup() throws IOException {
        // 임시 디렉토리 생성
        tempDirectory = Files.createTempDirectory("tempDir");
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        Path path = tempDirectory.toAbsolutePath();
        registry.add("server.local.storage", path::toString);
    }

//    @Test
    @Transactional
    void testPath() throws IOException {
        File file = new File("src/test/java/com/hanpyeon/academyapi/media/storage/temp.png");
        FileInputStream imageInputStream;
        try {
            imageInputStream = new FileInputStream(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        localStorage.store(new ImageUploadFile(new MockMultipartFile("hee1.png", "origin.png", null, imageInputStream)));
    }
}