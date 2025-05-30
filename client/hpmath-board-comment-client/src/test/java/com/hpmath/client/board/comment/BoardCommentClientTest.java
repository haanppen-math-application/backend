package com.hpmath.client.board.comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestClientException;

@SpringBootTest
class BoardCommentClientTest {
    private static MockWebServer mockWebServer = new MockWebServer();
    @Autowired
    private BoardCommentClient boardCommentClient;
    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("client.board-comment.url", () -> "http://" + mockWebServer.getHostName() + ":" + mockWebServer.getPort());
    }

    @Test
    void 정상_응답_처리() throws JsonProcessingException {
        final BoardCommentClient.CommentDetail detail = new BoardCommentClient.CommentDetail(1L, "test", false, Collections.emptyList(), LocalDateTime.now(), 1L);

        MockResponse mockResponse = new MockResponse();
        mockResponse.setResponseCode(200);
        mockResponse.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        mockResponse.setBody(objectMapper.writeValueAsString(detail));

        mockWebServer.enqueue(mockResponse);

        Assertions.assertEquals(detail, boardCommentClient.getCommentDetail(1L));
    }

    @Test
    void 타임아웃_시_에러_및_로그() {
        final MockResponse mockResponse = new MockResponse();
        mockResponse.setHeadersDelay(10L, TimeUnit.SECONDS);
        mockWebServer.enqueue(mockResponse);

        Assertions.assertThrows(RestClientException.class, () -> boardCommentClient.getCommentDetail(Mockito.anyLong()));
    }

    @Test
    void 응답_400대_에러_및_로그() {
        final MockResponse mockResponse = new MockResponse();
        mockResponse.setResponseCode(400);
        mockWebServer.enqueue(mockResponse);

        Assertions.assertThrows(RestClientException.class, () -> boardCommentClient.getCommentDetail(Mockito.anyLong()));
    }

    @Test
    void 응답_500대면_에러_및_로그() {
        final MockResponse mockResponse = new MockResponse();
        mockResponse.setResponseCode(500);
        mockWebServer.enqueue(mockResponse);

        Assertions.assertThrows(RestClientException.class, () -> boardCommentClient.getCommentDetail(Mockito.anyLong()));
    }
}