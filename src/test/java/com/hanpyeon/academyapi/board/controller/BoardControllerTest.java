package com.hanpyeon.academyapi.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanpyeon.academyapi.board.config.QuestionPageRequest;
import com.hanpyeon.academyapi.board.config.QuestionPageRequestMethodArgumentResolver;
import com.hanpyeon.academyapi.board.dto.QuestionDetails;
import com.hanpyeon.academyapi.board.dto.QuestionPreview;
import com.hanpyeon.academyapi.board.dto.QuestionRegisterRequestDto;
import com.hanpyeon.academyapi.board.mapper.BoardMapper;
import com.hanpyeon.academyapi.board.service.BoardService;
import com.hanpyeon.academyapi.security.filter.JwtAuthenticationFilter;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    BoardMapper boardMapper;
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
        ).andExpect(status().isBadRequest());
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
        ).andExpect(status().isCreated())
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
                ).andExpect(status().isCreated());
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
        ).andExpect(status().isCreated());
    }

    @Test
    void 질문조회성공테스트() throws Exception {
        Mockito.when(boardService.getSingleQuestionDetails(Mockito.any()))
                        .thenReturn(Mockito.mock(QuestionDetails.class));
        mockMvc.perform(get("/api/board/12"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @ParameterizedTest
    @CsvSource({
            "0, 10",
            "1, 5",
            "2, 15"
    })
    void 페이지_조회_테스트(String pageNumber, String pageSize) throws Exception {
        ArgumentCaptor<QuestionPageRequest> captor = ArgumentCaptor.forClass(QuestionPageRequest.class);
        mockMvc.perform(get("/api/board")
                        .param("page", pageNumber)
                        .param("size", pageSize))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(boardService).loadLimitedQuestions(captor.capture());

        QuestionPageRequest questionPageRequest = captor.getValue();

        Assertions.assertEquals(questionPageRequest.getPageNumber(), Integer.parseInt(pageNumber));
        Assertions.assertEquals(questionPageRequest.getPageSize(), Integer.parseInt(pageSize));
    }
}