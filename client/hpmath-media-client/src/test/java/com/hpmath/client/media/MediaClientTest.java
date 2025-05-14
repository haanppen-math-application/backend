package com.hpmath.client.media;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class MediaClientTest {
    @Autowired
    private MediaClient mediaClient;

    @Test
    void test() {
        log.info(mediaClient.getFileInfo("testSrc").toString());
    }

}