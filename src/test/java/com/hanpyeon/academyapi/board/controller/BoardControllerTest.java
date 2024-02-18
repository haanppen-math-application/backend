package com.hanpyeon.academyapi.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanpyeon.academyapi.board.dto.QuestionRegisterRequestDto;
import com.hanpyeon.academyapi.board.mapper.QuestionMapper;
import com.hanpyeon.academyapi.board.service.BoardService;
import com.hanpyeon.academyapi.security.filter.JwtAuthenticationFilter;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@WebMvcTest(controllers = BoardController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class, // 추가
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class, JwtAuthenticationFilter.class})
        })
class BoardControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    QuestionMapper questionMapper;
    @MockBean
    BoardService boardService;
    @Test
    void dto_없음_실패_테스트() throws Exception {
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "helwijadw".getBytes());

        mockMvc.perform(multipart("/api/board")
                .file(image)
                .content(MediaType.MULTIPART_FORM_DATA_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    void 이미지_없음_성공_테스트() throws Exception {
        MockMultipartFile dto = new MockMultipartFile(
                "questionRegisterRequestDto",
                null,
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(new QuestionRegisterRequestDto(
                        "제목",
                        "내용",
                        12L
                )).getBytes()
        );
        
        mockMvc.perform(multipart("/api/board")
                .file(dto)
                .content(MediaType.MULTIPART_FORM_DATA_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void 이미지와_DTO_모두_존재_성공_테스트() throws Exception {
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "helwijadw".getBytes());

        MockMultipartFile dto = new MockMultipartFile(
                "questionRegisterRequestDto",
                null,
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(new QuestionRegisterRequestDto(
                        "제목",
                        "내용",
                        12L
                )).getBytes()
        );

        mockMvc.perform(multipart("/api/board")
                .file(image)
                .file(dto)
                .content(MediaType.MULTIPART_FORM_DATA_VALUE)
                ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void 여러_이미지_성공_테스트() throws Exception {
        MockMultipartFile image1 = new MockMultipartFile(
                "image",
                "helwijadw".getBytes());
        MockMultipartFile image2 = new MockMultipartFile(
                "image",
                "helwijadw".getBytes());

        MockMultipartFile dto = new MockMultipartFile(
                "questionRegisterRequestDto",
                null,
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(new QuestionRegisterRequestDto(
                        "제목",
                        "내용",
                        12L
                )).getBytes()
        );

        mockMvc.perform(multipart("/api/board")
                .file(image1)
                .file(image2)
                .file(dto)
                .content(MediaType.MULTIPART_FORM_DATA_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }
}