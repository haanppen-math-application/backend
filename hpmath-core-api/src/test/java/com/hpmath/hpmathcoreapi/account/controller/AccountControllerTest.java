package com.hpmath.hpmathcoreapi.account.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpmath.HpmathCoreApiApplication;
import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcoreapi.account.service.AccountPasswordRefreshService;
import com.hpmath.hpmathcoreapi.account.service.AccountQueryService;
import com.hpmath.hpmathcoreapi.account.service.AccountRegisterService;
import com.hpmath.hpmathcoreapi.account.service.AccountRemoveService;
import com.hpmath.hpmathcoreapi.account.service.AccountUpdateService;
import com.hpmath.hpmathwebcommon.JwtUtils;
import com.hpmath.hpmathwebcommon.PasswordHandler;
import com.hpmath.hpmathwebcommon.TestAuthorization;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@WebMvcTest(
        controllers = {AccountController.class}
)
@ContextConfiguration(classes = {HpmathCoreApiApplication.class})
@ActiveProfiles("test")
@TestAuthorization
public class AccountControllerTest {
    private static final String BASE_URL = "/api/accounts";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    AccountUpdateService accountUpdateService;
    @MockitoBean
    AccountRemoveService accountRemoveService;
    @MockitoBean
    AccountRegisterService accountRegisterService;
    @MockitoBean
    AccountPasswordRefreshService accountPasswordRefreshService;
    @MockitoBean
    PasswordHandler passwordHandler;
    @MockitoBean
    AccountQueryService queryService;
    @MockitoBean
    JwtUtils jwtUtils;

    @ParameterizedTest
    @MethodSource("provideIllegalArguments")
    void 잘못된_요청_테스트(Map<String, Object> requestDto) throws Exception {
        postRequestWithAdminRole(status().isBadRequest(), requestDto);
    }

    @ParameterizedTest
    @MethodSource("provideLegalArguments")
    void 옳은_요청_테스트(Map<String, Object> requestDto) throws Exception {
        postRequestWithAdminRole(status().isCreated(), requestDto);
    }

    private void postRequestWithAdminRole(ResultMatcher resultMatcher, Map<String, Object> requestDto)
            throws Exception {
        mockMvc.perform(post(BASE_URL)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .requestAttr("role", Role.ADMIN)
        ).andExpect(resultMatcher);
    }

    private static Map<String, Object> createRequest(String name, Integer grade, String phoneNumber, String role, String password) {
        Map<String, Object> body = new HashMap<>();
        body.put("name", name);
        body.put("grade", grade);
        body.put("phoneNumber", phoneNumber);
        body.put("role", role);
        body.put("password", password);
        return body;
    }

    public static Stream<Arguments> provideLegalArguments() {
        return Stream.of(
                Arguments.of(createRequest("희종", 0, "0102010021", "student", null)),
                Arguments.of(createRequest("TOM", 11, "0102010021", "student", null)),
                Arguments.of(createRequest("COKE", 10, "01022", "manager", null))
        );
    }

    public static Stream<Arguments> provideIllegalArguments() {
        return Stream.of(
                Arguments.of(createRequest("Tom", null, null, "student", null)),
                Arguments.of(createRequest(null, 10, "010101001", "student", null)),
                Arguments.of(createRequest("Tom", null, "010101001", null, null)),
                Arguments.of(createRequest("Tom", 10, null, null, null)),

                Arguments.of(createRequest("Heejong", 10, "0101010101ㅂㅈㄱㄷ", null, null)),
                Arguments.of(createRequest("Tom", 10, "ewfjnede", "student", null)),
                Arguments.of(createRequest("희종", 12, "10100101001", null, null)),
                Arguments.of(createRequest("희종", -1, "10100101001", "student", null))
        );
    }
}