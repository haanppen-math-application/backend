package com.hpmath.academyapi.course.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpmath.academyapi.course.application.port.in.AddStudentToCourseUseCase;
import com.hpmath.academyapi.course.application.port.in.CourseRegisterUseCase;
import com.hpmath.academyapi.course.application.port.in.DeleteCourseUseCase;
import com.hpmath.academyapi.course.application.port.in.UpdateCourseStudentsUseCase;
import com.hpmath.academyapi.course.application.port.in.UpdateCourseUseCase;
import com.hpmath.academyapi.course.controller.Requests.CourseRegisterRequest;
import com.hpmath.academyapi.course.controller.Requests.RegisterStudentRequest;
import com.hpmath.academyapi.security.Role;
import com.hpmath.academyapi.security.authentication.JwtAuthenticationToken;
import com.hpmath.academyapi.security.authentication.MemberPrincipal;
import com.hpmath.academyapi.security.filter.JwtAuthenticationFilter;
import java.util.List;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = CourseController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class, // 추가
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class, JwtAuthenticationFilter.class})
        })
class CourseControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    CourseRegisterUseCase courseRegisterUseCase;
    @MockBean
    AddStudentToCourseUseCase addStudentToCourseUseCase;
    @MockBean
    DeleteCourseUseCase deleteCourseAdapter;
    @MockBean
    UpdateCourseUseCase updateCourseNameUseCase;
    @MockBean
    UpdateCourseStudentsUseCase updateCourseStudentsUseCase;

    @Test
    void test() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/manage/courses/{courseId}", 1))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Autowired
    ObjectMapper objectMapper;
    @Test
    void 학생등록요청_테스트() throws Exception {
        RegisterStudentRequest request = new Requests.RegisterStudentRequest(1l, List.of(2l,3l));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/manage/courses/students")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    void 학생_NULL_테스트_에러처리() throws Exception {
        RegisterStudentRequest request = new Requests.RegisterStudentRequest(1l, null);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/manage/courses/students")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    void 등록할_반_NULL_에러처리() throws Exception {
        RegisterStudentRequest request = new Requests.RegisterStudentRequest(null, List.of(1l));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/manage/courses/students")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @BeforeEach
    void init() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(JwtAuthenticationToken.authenticated("test", new MemberPrincipal(1l, "test", Role.STUDENT), null));
    }

    @Test
    void 반_등록_테스트() throws Exception {

        Mockito.when(courseRegisterUseCase.register(Mockito.any()))
                .thenReturn(1l);

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/manage/courses")
                        .content(objectMapper.writeValueAsBytes(new CourseRegisterRequest("12", 1l, List.of(1l))))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
    }
}