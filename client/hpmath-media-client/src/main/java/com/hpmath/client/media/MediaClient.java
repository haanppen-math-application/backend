package com.hpmath.client.media;

import com.hpmath.client.common.ClientExceptionMapper;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class MediaClient {
    @Autowired
    private ClientExceptionMapper exceptionMapper;
    private final RestClient restClient;

    public MediaClient(
            @Value("${client.media.url:http://localhost:80}") String baseUrl
    ) {
        log.info("baseUrl initialized with  = {}", baseUrl);
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(timeoutConfigurationFactory())
                .build();
    }

    private static ClientHttpRequestFactory timeoutConfigurationFactory() {
        final SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(Duration.ofSeconds(1));
        clientHttpRequestFactory.setReadTimeout(Duration.ofSeconds(1));
        return clientHttpRequestFactory;
    }

    public MediaInfo getFileInfo(String mediaSrc) {
        final MediaInfo mediaInfo = exceptionMapper.mapException(() -> restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/inner/v1/media/info")
                        .queryParam("mediaSrc", mediaSrc)
                        .build())
                .retrieve()
                .body(MediaInfo.class));
        log.debug("mediaInfo: {}", mediaInfo);
        return mediaInfo;
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
