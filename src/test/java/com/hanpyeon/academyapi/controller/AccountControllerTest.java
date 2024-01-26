package com.hanpyeon.academyapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanpyeon.academyapi.dto.RegisterRequestDto;
import com.hanpyeon.academyapi.security.Role;
import com.hanpyeon.academyapi.service.RegisterService;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class, // 추가
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        })
public class AccountControllerTest {
    private static final String BASE_URL = "/api/accounts";
    private static final Logger LOGGER = LoggerFactory.getLogger("계졍 컨트롤러 테스트");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    RegisterService studentRegisterService;

    @ParameterizedTest
    @MethodSource("provideIllegalArguments")
    void 학생등록_에러처리_테스트(String name, Integer grade, String phone, Role role, String password) throws Exception {
        request(status().isBadRequest(), createRequest(name, grade, phone, role, password));
    }

    @ParameterizedTest
    @MethodSource("provideLegalArguments")
    void 학생등록_성공_테스트(String name, Integer grade, String phone, Role role, String password) throws Exception {
        request(status().isCreated(), createRequest(name, grade, phone, role, password));
    }

    private void request(ResultMatcher resultMatcher, RegisterRequestDto requestDto) throws Exception {
        LOGGER.info(objectMapper.writeValueAsString(requestDto));
        mockMvc.perform(post(BASE_URL)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf())
        ).andExpect(resultMatcher);
    }

    private RegisterRequestDto createRequest(String name, Integer grade, String phoneNumber, Role role, String password) {
        return new RegisterRequestDto(name, grade, phoneNumber, role, password);
    }

    public static Stream<Arguments> provideLegalArguments() {
        return Stream.of(
                Arguments.of("희종", 0, "102010021", Role.ROLE_STUDENT, null),
                Arguments.of("희종", 0, "1020110021", Role.ROLE_STUDENT, null),
                Arguments.of("희종", 0, "01012345678", Role.ROLE_STUDENT, null),
                Arguments.of("TOM", 11, "102010021", Role.ROLE_STUDENT, null)
        );
    }

    public static Stream<Arguments> provideIllegalArguments() {
        return Stream.of(
                Arguments.of(null, null, null, null, null),
                Arguments.of(null, 10, "010101001",Role.ROLE_STUDENT, null),
                Arguments.of("Tom", null, "010101001",null, null),
                Arguments.of("Tom", 10, null, null,null),
                Arguments.of("Heejong", 10, "0101010101ㅂㅈㄱㄷ",null, null),
                Arguments.of("Tom", 10, "ewfjnede",null, null),
                Arguments.of("희종", 12, "10100101001", null,null),
                Arguments.of("희종", -1, "10100101001", null,null)
        );
    }
}