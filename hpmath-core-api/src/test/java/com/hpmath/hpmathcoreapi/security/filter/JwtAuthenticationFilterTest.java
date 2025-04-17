package com.hpmath.hpmathcoreapi.security.filter;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hpmath.hpmathcoreapi.exception.ErrorCode;
import com.hpmath.hpmathcoreapi.security.JwtUtils;
import com.hpmath.hpmathcoreapi.security.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class JwtAuthenticationFilterTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ApplicationContext context;

    @Autowired
    JwtUtils jwtUtils;

    @Test
    void 잘못된_JWT_테스트() throws Exception {
        mockMvc.perform(post("/api/accounts").header(JwtUtils.HEADER, JwtUtils.TOKEN_TYPE + " " + "rfeefew"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString(ErrorCode.AUTHENTICATION_FAILED_EXCEPTION.getErrorCode())));
    }

    @Test
    void 조작된_JWT_테스트() throws Exception {
        String testToken = jwtUtils.generateAccessToken((long) 12, Role.STUDENT, null) + "12";
        mockMvc.perform(get("/any").header(JwtUtils.HEADER, testToken))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString(ErrorCode.AUTHENTICATION_FAILED_EXCEPTION.getErrorCode())));
    }

    @Test
    void 부적절한_JWT_테스트() throws Exception {
        String testToken = jwtUtils.generateAccessToken((long) 12, Role.STUDENT, null);
        mockMvc.perform(get("/any").header(JwtUtils.HEADER, testToken))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString(ErrorCode.AUTHENTICATION_FAILED_EXCEPTION.getErrorCode())));
    }
}