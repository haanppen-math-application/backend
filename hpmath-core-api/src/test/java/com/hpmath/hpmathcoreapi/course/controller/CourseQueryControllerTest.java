package com.hpmath.hpmathcoreapi.course.controller;

import com.hpmath.hpmathcoreapi.course.application.LoadCoursesByStudentQueryService;
import com.hpmath.hpmathcoreapi.course.application.port.in.LoadCourseDetailsQuery;
import com.hpmath.hpmathcoreapi.course.application.port.in.QueryAllCourseUseCase;
import com.hpmath.hpmathcoreapi.course.application.port.in.QueryCourseByMemberIdUseCase;
import com.hpmath.hpmathcoreapi.security.filter.JwtAuthenticationFilter;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = CourseQueryController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class, // 추가
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class, JwtAuthenticationFilter.class})
        })
class CourseQueryControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    QueryCourseByMemberIdUseCase loadCoursesByTeacherQuery;
    @MockBean
    LoadCourseDetailsQuery loadCourseDetailsQuery;
    @MockBean
    LoadCoursesByStudentQueryService loadCoursesByStudentQueryService;
    @MockBean
    QueryAllCourseUseCase queryAllCourseUseCase;


    @Test
    void 선생수업조회() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/courses/teachers/{teacherId}", 1l))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void 학생_수업조회() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/courses/students/{studentId}", 1l))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void 수업조회() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/manage/courses/{courseId}", 1l))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}