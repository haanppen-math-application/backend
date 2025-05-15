package com.hpmath.client.board.view;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class BoardViewClient {
    private final RestClient restClient;

    public BoardViewClient(
            @Value("${client.board-view.url:http://localhost:80}") final String baseUrl
    ) {
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

    public Long getViewCount(final Long boardId) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/inner/v1/board/view-count")
                        .queryParam("boardId", boardId)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> log.warn("Error query view count {}", boardId))
                .body(BoardViewCount.class)
                .viewCount();
    }

    public Long increaseViewCount(final Long boardId, final Long memberId) {
        return restClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/inner/v1/board/view-count")
                        .queryParam("boardId", boardId)
                        .queryParam("memberId", memberId)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> log.warn("Error increase view count {}", boardId))
                .body(BoardViewCount.class)
                .viewCount();
    }

    private record BoardViewCount(
            Long viewCount
    ) {
    }
}
