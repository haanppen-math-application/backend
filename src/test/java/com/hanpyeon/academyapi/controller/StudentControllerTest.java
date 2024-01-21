package com.hanpyeon.academyapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanpyeon.academyapi.dto.StudentRegisterRequestDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest
    @MethodSource("provideIllegalArguments")
    void 학생등록_에러처리_테스트(String name, Integer grade, String phone) throws Exception {
        mockMvc.perform(post("")
                .content(objectMapper.writeValueAsString(new StudentRegisterRequestDto(name, grade, phone)))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @MethodSource("provideLegalArguments")
    void 학생등록_성공_테스트(String name, Integer grade, String phone) throws Exception {
        mockMvc.perform(post("")
                        .content(objectMapper.writeValueAsString(new StudentRegisterRequestDto(name, grade, phone)))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }
    public static Stream<Arguments> provideLegalArguments() {
        return Stream.of(
                Arguments.of("희종", 0, "102010021"),
                Arguments.of("희종", 0, "102010021"),
                Arguments.of("희종", 0, "01012345678"),
                Arguments.of("TOM", 11, "102010021")
        );
    }
    public static Stream<Arguments> provideIllegalArguments() {
        return Stream.of(
                Arguments.of(null, null, null),
                Arguments.of(null, 10, "010101001"),
                Arguments.of("Tom", null, "010101001"),
                Arguments.of("Tom", 10, null),
                Arguments.of("Heejong", 10, "0101010101ㅂㅈㄱㄷ"),
                Arguments.of("Tom", 10, "ewfjnede")
        );
    }

}