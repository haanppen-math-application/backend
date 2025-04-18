package com.hpmath.hpmathcoreapi.course.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcoreapi.course.application.dto.CourseRegisterCommand;
import com.hpmath.hpmathcoreapi.course.application.port.out.LoadStudentsPort;
import com.hpmath.hpmathcoreapi.course.application.port.out.LoadTeacherPort;
import com.hpmath.hpmathcoreapi.course.application.port.out.RegisterCoursePort;
import com.hpmath.hpmathcoreapi.course.domain.Student;
import com.hpmath.hpmathcoreapi.course.domain.Teacher;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CourseRegisterServiceTest {

    @Mock
    LoadStudentsPort loadStudentsPort;
    @Mock
    LoadTeacherPort loadTeacherPort;
    @Mock
    RegisterCoursePort registerCoursePort;

    @InjectMocks
    CourseRegisterService courseRegisterService;

    @Test
    void 등록_테스트() {
        CourseRegisterCommand courseRegisterDto = new CourseRegisterCommand("1", 1l, null, 1l, Role.TEACHER);


        Mockito.when(registerCoursePort.register(Mockito.any()))
                .thenReturn(1l);
        Mockito.when(loadStudentsPort.loadStudents(Mockito.any()))
                .thenReturn(List.of(new Student(1l, "test", 12)));
        Mockito.when(loadTeacherPort.loadTeacher(Mockito.any()))
                .thenReturn(new Teacher(1l, "test"));

        assertThat(courseRegisterService.register(courseRegisterDto))
                .isEqualTo(1l);
    }
}