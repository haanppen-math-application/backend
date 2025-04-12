package com.hanpyeon.academyapi.dir.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanpyeon.academyapi.dir.controller.Requests.CreateDirectoryRequest;
import com.hanpyeon.academyapi.dir.service.DirectoryService;
import com.hanpyeon.academyapi.security.JwtUtils;
import com.hanpyeon.academyapi.security.Role;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DirectoryControllerTest {

    @MockBean
    DirectoryService directoryService;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AuthenticationManager authenticationManager;
    @ParameterizedTest
    @MethodSource("provideLegalRoles")
    void 디렉토리생성_권한_성공테스트(final Role role) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/directories")
                        .header(JwtUtils.HEADER, jwtUtils.generateAccessToken(1l, role, "test"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new CreateDirectoryRequest("/test", "test", false, false))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void 디렉토리생성_권한_실패테스트() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/directories")
                        .header(JwtUtils.HEADER, jwtUtils.generateAccessToken(1l, Role.STUDENT, "test"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CreateDirectoryRequest("test", "test", false, false))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    static Stream<Arguments> provideLegalRoles() {
        return Stream.of(Arguments.of(Role.MANAGER),
                Arguments.of(Role.ADMIN),
                Arguments.of(Role.TEACHER));
    }
}