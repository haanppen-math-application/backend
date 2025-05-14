package com.hpmath.client.media;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class MediaClient {
    private final RestClient restClient;

    public MediaClient(@Value("${client.media.url:http://localhost}") String baseUrl) {
        log.info("baseUrl initialized with  = {}", baseUrl);
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    public MediaInfo getFileInfo(String mediaSrc) {
        return restClient.get()
                .uri("/api/inner/v1/media/info?mediaSrc=" + mediaSrc)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> log.warn("Error getting file info {}", mediaSrc))
                .body(MediaInfo.class);
    }

    public record MediaInfo(
            String mediaName,
            String mediaSrc,
            LocalDateTime createdTime,
            Long runtimeDuration,
            Long fileSize
    ){
    }
}
