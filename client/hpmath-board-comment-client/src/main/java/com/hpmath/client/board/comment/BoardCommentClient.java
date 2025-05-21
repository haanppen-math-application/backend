package com.hpmath.client.board.comment;

import com.hpmath.common.Role;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

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
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/inner/v1/board/comments/detail")
                        .queryParam("commentId", commentId)
                        .build())
                .retrieve()
                .body(CommentDetail.class);
    }

    public CommentDetails getCommentDetails(final Long questionId) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/inner/v1/board/comments")
                        .queryParam("questionId", questionId)
                        .build())
                .retrieve()
                .body(CommentDetails.class);
    }

    public record CommentDetails(
            List<CommentDetail> commentDetails
    ) {
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
