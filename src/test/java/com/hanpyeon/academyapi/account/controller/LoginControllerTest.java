package com.hanpyeon.academyapi.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanpyeon.academyapi.account.service.LoginService;
import com.hanpyeon.academyapi.security.filter.JwtAuthenticationFilter;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LoginController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class, // 추가
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class, JwtAuthenticationFilter.class})
        })
class LoginControllerTest {
    private static final String BASE_URL = "/api/login";

    @MockBean
    LoginService loginService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @ParameterizedTest
    @MethodSource("provideLegalRequest")
    void 로그인_형식_성공_테스트(Map<String, String> requestDto) throws Exception {
        request(status().isOk(), requestDto);
    }

    @ParameterizedTest
    @MethodSource("provideIllegalRequest")
    void 로그인_형식_실패_테스트(Map<String, String> requestDto) throws Exception {
        request(status().isBadRequest(), requestDto);
    }

    private void request(ResultMatcher resultMatcher, Map<String, String> requestDto) throws Exception {
        mockMvc.perform(post(BASE_URL)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf())
        ).andExpect(resultMatcher);
    }

    static Stream<Arguments> provideLegalRequest() {
        return Stream.of(
                Arguments.of(createRequest("124332", "1wqefs23")),
                Arguments.of(createRequest("32143", "12321"))
        );
    }

    static Stream<Arguments> provideIllegalRequest() {
        return Stream.of(
                Arguments.of(createRequest(null, null)),
                Arguments.of(createRequest(null, "1231")),
                Arguments.of(createRequest("1242", null)),
                Arguments.of(createRequest("", "")),
                Arguments.of(createRequest("", "211324")),
                Arguments.of(createRequest("211324", "")),
                Arguments.of(createRequest("qwefdeqw", "qwq31")),
                Arguments.of(createRequest("qwefdeqw", "1232"))
        );
    }

    static Map<String, String> createRequest(String phoneNumber, String passowrd) {
        Map<String, String> body = new HashMap<>();
        body.put("userPhoneNumber", phoneNumber);
        body.put("password", passowrd);
        return body;
    }
}