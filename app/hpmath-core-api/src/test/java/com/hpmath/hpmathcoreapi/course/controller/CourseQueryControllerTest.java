package com.hpmath.hpmathcoreapi.course.controller;

import com.hpmath.HpmathCoreApiApplication;
import com.hpmath.hpmathcoreapi.course.application.LoadCoursesByStudentQueryService;
import com.hpmath.hpmathcoreapi.course.application.port.in.LoadCourseDetailsQuery;
import com.hpmath.hpmathcoreapi.course.application.port.in.QueryAllCourseUseCase;
import com.hpmath.hpmathcoreapi.course.application.port.in.QueryCourseByMemberIdUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@WebMvcTest(
        controllers = {CourseQueryController.class},
        excludeFilters = {
                @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = OncePerRequestFilter.class),
                @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfigurer.class)
        }
)
@ContextConfiguration(classes = {HpmathCoreApiApplication.class})
@ActiveProfiles("test")
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