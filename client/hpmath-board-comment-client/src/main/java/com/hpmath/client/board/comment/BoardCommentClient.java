package com.hpmath.client.board.comment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Slf4j
@Component
public class BoardCommentClient {
    private final RestClient restClient;

    public BoardCommentClient(
            @Value("${client.board-comment.url:http://localhost:80}") final String baseUrl
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

    public CommentDetail getCommentDetail(final Long commentId) {
        return logExceptions(() -> restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/inner/v1/board/comments/detail")
                        .queryParam("commentId", commentId)
                        .build())
                .retrieve()
                .body(CommentDetail.class));
    }

    public List<CommentDetail> getCommentDetails(final Long questionId) {
        return logExceptions(() -> restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/inner/v1/board/comments")
                        .queryParam("questionId", questionId)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentDetail>>() {}));
    }

    private <T> T logExceptions(final Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (final RestClientException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    public record CommentDetail(
            Long commentId,
            String content,
            Boolean selected,
            List<ImageUrlResult> images,
            LocalDateTime registeredDateTime,
            Long ownerId
    ) {
    }

    public record ImageUrlResult(
            String imageUrl
    ) {
    }
}
