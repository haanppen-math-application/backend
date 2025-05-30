package com.hpmath.client.board.question;

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
public class BoardQuestionClient {
    private final RestClient restClient;

    public BoardQuestionClient(
            @Value("${client.board-question.url:http://localhost:80}") final String baseUrl
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

    public List<QuestionDetailInfo> getQuestionsSortByDate(final int pageNumber, final int pageSize) {
        return logExceptions(() -> restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/inner/v1/questions/paged/date-desc")
                        .queryParam("pageSize", pageSize)
                        .queryParam("pageNumber", pageNumber)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<List<QuestionDetailInfo>>() {}));
    }

    public QuestionDetailInfo get(final Long questionId) {
        return logExceptions(() -> restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/inner/v1/questions/detail")
                        .queryParam("questionId", questionId)
                        .build())
                .retrieve()
                .body(QuestionDetailInfo.class));
    }

    public Long getCount() {
        return logExceptions(() -> restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/inner/v1/questions/count")
                        .build())
                .retrieve()
                .body(Long.class));
    }

    private <T> T logExceptions(final Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (final RestClientException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    public record QuestionDetailInfo(
            Long questionId,
            String title,
            String content,
            Boolean solved,
            LocalDateTime registeredDateTime,
            Long ownerId,
            Long targetId,
            List<String> mediaSrcs
    ) {
    }
}
