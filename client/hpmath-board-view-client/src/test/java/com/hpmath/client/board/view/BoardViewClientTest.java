package com.hpmath.client.board.view;

import com.hpmath.client.common.ClientBadRequestException;
import com.hpmath.client.common.ClientRequestTimeoutException;
import com.hpmath.client.common.ClientServerException;
import java.util.concurrent.TimeUnit;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestClientException;

@SpringBootTest
class BoardViewClientTest {
    private static final MockWebServer mockWebServer = new MockWebServer();
    @Autowired
    private BoardViewClient boardViewClient;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("client.board-view.url", () -> "http://" + mockWebServer.getHostName() + ":" + mockWebServer.getPort());
    }

    @Test
    void 응답코드_400대_에러처리() {
        final MockResponse mockResponse = new MockResponse();
        mockResponse.setResponseCode(400);

        mockWebServer.enqueue(mockResponse);
        Assertions.assertThrows(ClientBadRequestException.class, () -> boardViewClient.getViewCount(10L));
    }

    @Test
    void 응답코드_500대_에러처리() {
        final MockResponse mockResponse = new MockResponse();
        mockResponse.setResponseCode(500);

        mockWebServer.enqueue(mockResponse);
        Assertions.assertThrows(ClientServerException.class, () -> boardViewClient.getViewCount(10L));
    }

    @Test
    void 타임아웃_시_에러처리() {
        final MockResponse mockResponse = new MockResponse();
        mockResponse.setHeadersDelay(10L, TimeUnit.SECONDS);

        mockWebServer.enqueue(mockResponse);
        Assertions.assertThrows(ClientRequestTimeoutException.class, () -> boardViewClient.getViewCount(10L));
    }
}